package com.stathis.calculator.features.currencies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.stathis.calculator.model.CurrencyRate
import com.stathis.calculator.model.NewResponseModel
import com.stathis.calculator.network.ApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrenciesViewModel : ViewModel() {

    val data = MutableLiveData<String>()
    val wrongInput = MutableLiveData<Boolean>()
    val equation = MutableLiveData<String>()
    val firstEquation = MutableLiveData<String>()
    val secondEquation = MutableLiveData<String>()

    fun validateInput(currencyFrom: String, currencyTo: String, amount: String) {
        when (currencyFrom.isNullOrEmpty() || currencyTo.isNullOrEmpty() || amount.isNullOrEmpty()) {
            true -> wrongInput.value = true
            false -> getRates(amount.toDouble(), currencyFrom, currencyTo)
        }
    }

    fun getRates(
        amount: Double,
        currencyFrom: String,
        currencyTo: String
    ) {
        ApiClient.getLatest().enqueue(object : Callback<NewResponseModel> {
            override fun onResponse(call: Call<NewResponseModel>, response: Response<NewResponseModel>) {
                val json = Gson().toJson(response.body())
                val newResponse = JSONObject(json)

                newResponse.keys().forEach {
                    when (it == "rates") {
                        true -> {
                            val rates = newResponse.getString("rates")

                            val response = JSONObject(rates)
                            val list = mutableListOf<CurrencyRate>()

                            response.keys().forEach { rateName ->
                                val myRate = response.getString(rateName).toDouble()
                                list.add(CurrencyRate(rateName, myRate))
                            }

                            calculateRate(list, currencyFrom, currencyTo, amount)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NewResponseModel>, t: Throwable) {
                data.value = null
            }
        })
    }

    private fun calculateRate(
        response: List<CurrencyRate>,
        currencyFrom: String,
        currencyTo: String,
        amount: Double
    ) {
        var currencyRateFrom = 0.0
        var currencyRateTo = 0.0

        response.forEach {
            if (it.name == currencyFrom) {
                currencyRateFrom = it.rate
            } else if (it.name == currencyTo) {
                currencyRateTo = it.rate
            }
        }

        getFinalResult(currencyRateFrom, currencyRateTo, amount, currencyFrom, currencyTo)
        getFirstEquation(currencyRateFrom, currencyRateTo, currencyFrom, currencyTo)
        getSecondEquation(currencyRateFrom, currencyRateTo, currencyFrom, currencyTo)
    }

    private fun getFinalResult(
        currencyRateFrom: Double,
        currencyRateTo: Double,
        amount: Double,
        startCurCode: String,
        endCurCode: String
    ) {
        val x = currencyRateTo * (1 / currencyRateFrom)
        val result = x * amount
        val rounded = String.format("%.3f", result)
        data.value = String.format("%.3f", result)

        equation.value = "$amount $startCurCode = $rounded $endCurCode"
    }

    private fun getFirstEquation(
        currencyRateFrom: Double,
        currencyRateTo: Double,
        startCurCode: String,
        endCurCode: String
    ) {
        val x = currencyRateTo * (1 / currencyRateFrom)
        val rounded = String.format("%.3f", x)
        val test = "1 $startCurCode = $rounded $endCurCode"
        Log.d("",test)

        firstEquation.value = test
    }

    private fun getSecondEquation(
        currencyRateFrom: Double,
        currencyRateTo: Double,
        startCurCode: String,
        endCurCode: String
    ) {
        val x = currencyRateFrom * (1 / currencyRateTo)
        val rounded = String.format("%.3f", x)
        val test = "1 $endCurCode = $rounded $startCurCode"
        Log.d("",test)

        secondEquation.value = test
    }
}