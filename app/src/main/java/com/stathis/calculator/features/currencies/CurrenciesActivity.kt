package com.stathis.calculator.features.currencies

import androidx.lifecycle.ViewModelProvider
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity

class CurrenciesActivity : SimplifiedActivity(R.layout.activity_currencies) {

    private lateinit var viewModel : CurrenciesViewModel

    override fun init() {
        viewModel = ViewModelProvider(this).get(CurrenciesViewModel::class.java)
    }

    override fun startOps() {
        /*
        FIXME: Create UI layout for Currencies screen according to zeplin screen
         */
    }

    override fun stopOps() {
        //
    }
}