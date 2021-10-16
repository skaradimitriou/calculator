package com.stathis.calculator.network

import com.stathis.calculator.model.NewResponseModel
import com.stathis.calculator.util.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val api: CalculatorApi

    init {
        api = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CalculatorApi::class.java)
    }

    fun getLatest(): Call<NewResponseModel> {
        return api.getLatest()
    }
}