package com.stathis.calculator.features.currencies

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity
import kotlinx.android.synthetic.main.activity_currencies.*

class CurrenciesActivity : SimplifiedActivity(R.layout.activity_currencies) {

    private lateinit var viewModel: CurrenciesViewModel
    private var amount = 0.0

    override fun init() {
        viewModel = ViewModelProvider(this).get(CurrenciesViewModel::class.java)
    }

    override fun startOps() {
        val result = intent.getStringExtra("AMOUNT") ?: "0.0"

        result?.let { amount = it.toDouble() }

        start_currency_value.setText(amount.toString())

        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, currencies)

        start_cur_value.setAdapter(arrayAdapter)
        end_cur_value.setAdapter(arrayAdapter)

        convert_btn.setOnClickListener {
            val currencyFrom = start_cur_value.text.toString()
            val currencyTo = end_cur_value.text.toString()
            val amount = start_currency_value.text.toString()

            when (currencyFrom.isNotEmpty() && currencyTo.isNotEmpty() && amount.isNotEmpty()) {
                true -> viewModel.validateInput(currencyFrom, currencyTo, amount)
                else -> notifyUser(resources.getString(R.string.currencies_error_msg))
            }
        }

        viewModel.data.observe(this, Observer {
            when (it.isNullOrEmpty()) {
                true -> notifyUser(resources.getString(R.string.api_error_msg))
                else -> ending_currency_value.setText(it)
            }
        })

        viewModel.equation.observe(this, Observer {
            convert_description.text = it
        })

        viewModel.firstEquation.observe(this, Observer {
            convert_start_currency.text = it
        })

        viewModel.secondEquation.observe(this, Observer {
            convert_end_currency.text = it
        })

        viewModel.wrongInput.observe(this, Observer {
            when (it) {
                true -> notifyUser(resources.getString(R.string.currencies_error_msg))
                false -> Unit
            }
        })
    }

    private fun notifyUser(message: String) {
        Snackbar.make(findViewById(R.id.currencies_screen_parent), message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun stopOps() {
        viewModel.data.removeObservers(this)
        viewModel.equation.removeObservers(this)
        viewModel.firstEquation.removeObservers(this)
        viewModel.secondEquation.removeObservers(this)
        viewModel.wrongInput.removeObservers(this)
    }
}