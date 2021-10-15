package com.stathis.calculator.model

data class ResponseModel(
    val success: Boolean,
    val base: String,
    val date: String,
    val rates: RatesModel
)
