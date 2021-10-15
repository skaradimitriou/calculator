package com.stathis.calculator.network

import com.stathis.calculator.model.ResponseModel
import com.stathis.calculator.util.API_KEY
import retrofit2.Call
import retrofit2.http.GET

interface CalculatorApi {

    @GET("latest?access_key=$API_KEY")
    fun getLatest(): Call<ResponseModel>
}