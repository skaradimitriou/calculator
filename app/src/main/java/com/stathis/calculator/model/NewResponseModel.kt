package com.stathis.calculator.model

data class NewResponseModel(
    val base: String,
    val date: String,
    val rates: RatesModel?,
    val success: Boolean,
    val timestamp: Int
)