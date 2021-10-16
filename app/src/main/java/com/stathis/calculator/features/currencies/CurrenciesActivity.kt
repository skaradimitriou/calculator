package com.stathis.calculator.features.currencies

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity
import kotlinx.android.synthetic.main.activity_currencies.*

class CurrenciesActivity : SimplifiedActivity(R.layout.activity_currencies) {

    private lateinit var viewModel : CurrenciesViewModel
    private var amount = 0

    override fun init() {
        viewModel = ViewModelProvider(this).get(CurrenciesViewModel::class.java)
    }

    override fun startOps() {
        /*
        FIXME: Create UI layout for Currencies screen according to zeplin screen
         */
        val result = intent.getStringExtra("AMOUNT") ?: ""
        result?.let { amount = it.toInt() }

        Log.d("",amount.toString())

        start_currency_value.text = amount.toString()
    }

    override fun stopOps() {
        //
    }
}