package com.stathis.calculator.features.calculator

import androidx.lifecycle.ViewModelProvider
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity

class CalculatorActivity : SimplifiedActivity(R.layout.activity_calculator) {

    private lateinit var viewModel : CalculatorViewModel

    override fun init() {
       viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
    }

    override fun startOps() {
        /*
        FIXME: Create UI layout for Calculator screen according to zeplin screen
         */
    }

    override fun stopOps() {
        //
    }
}