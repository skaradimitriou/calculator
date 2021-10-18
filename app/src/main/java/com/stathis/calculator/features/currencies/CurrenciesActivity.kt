package com.stathis.calculator.features.currencies

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity
import kotlinx.android.synthetic.main.activity_currencies.*

class CurrenciesActivity : SimplifiedActivity(R.layout.activity_currencies) {

    private lateinit var viewModel : CurrenciesViewModel
    private var amount = 0.0

    override fun init() {
        viewModel = ViewModelProvider(this).get(CurrenciesViewModel::class.java)
    }

    override fun startOps() {
        val result = intent.getStringExtra("AMOUNT") ?: ""
        result?.let { amount = it.toDouble() }

        Log.d("",amount.toString())

        start_currency_value.text = amount.toString()

        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item,currencies)

        start_cur_value.setAdapter(arrayAdapter)
        end_cur_value.setAdapter(arrayAdapter)

        convert_btn.setOnClickListener {
            val currencyFrom = start_cur_value.text.toString()
            val currencyTo = end_cur_value.text.toString()
            val amount = start_currency_value.text.toString()

            viewModel.validateInput(currencyFrom,currencyTo,amount)
        }

        viewModel.data.observe(this, Observer {
            ending_currency_value.text = it
        })

        viewModel.equation.observe(this, Observer {
            convert_description.text = it
        })

        viewModel.wrongInput.observe(this, Observer{
            when(it){
                //FIXME: Save the final output text to resources
                true -> Toast.makeText(this,"Please select the currencies that you want to convert", Toast.LENGTH_LONG).show()
                false -> Unit
            }
        })
    }

    override fun stopOps() {
        viewModel.release(this)
    }
}