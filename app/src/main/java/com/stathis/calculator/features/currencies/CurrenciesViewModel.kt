package com.stathis.calculator.features.currencies

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.calculator.model.NewResponseModel
import com.stathis.calculator.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrenciesViewModel : ViewModel() {

    val data = MutableLiveData<NewResponseModel>()

    init {
        getRates()
    }

    fun getRates() {
        ApiClient.getLatest().enqueue(object : Callback<NewResponseModel> {
            override fun onResponse(call: Call<NewResponseModel>, response: Response<NewResponseModel>) {
                val test = response.body()
                Log.d("",test.toString())
                data.value = response.body()
            }

            override fun onFailure(call: Call<NewResponseModel>, t: Throwable) {
                data.value = null
            }
        })
    }

    fun observe(owner : LifecycleOwner){

    }

    fun release(owner: LifecycleOwner){

    }
}