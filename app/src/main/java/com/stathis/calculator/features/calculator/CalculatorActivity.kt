package com.stathis.calculator.features.calculator

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity
import com.stathis.calculator.features.currencies.CurrenciesActivity
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : SimplifiedActivity(R.layout.activity_calculator) {

    private lateinit var viewModel : CalculatorViewModel

    override fun init() {
       viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
    }

    override fun startOps() {
        /*
        FIXME: Create UI layout for Calculator screen according to zeplin screen
         */

        currencies_btn.setOnClickListener {
            startActivity(Intent(this,CurrenciesActivity::class.java).also {
                it.putExtra("AMOUNT",13500)
            })
        }
    }

    override fun stopOps() {
        //
    }
}