package com.stathis.calculator.features.calculator

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stathis.calculator.R
import com.stathis.calculator.abstraction.SimplifiedActivity
import com.stathis.calculator.features.currencies.CurrenciesActivity
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : SimplifiedActivity(R.layout.activity_calculator) {

    private lateinit var viewModel: CalculatorViewModel
    private var operationEnabled = false
    private var decimalEnabled = true

    override fun init() {
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
    }

    override fun startOps() {
        currencies_btn.setOnClickListener {
            val amount = calculator_result.text.toString()
            startActivity(Intent(this, CurrenciesActivity::class.java).also {
                it.putExtra("AMOUNT", amount)
            })
        }

        viewModel.result.observe(this, Observer{
            calculator_result.text = it
        })
    }

    override fun stopOps() {
        viewModel.result.removeObservers(this)
    }

    fun allClearAction(view: View) {
        calculator_operations.text = resources.getString(R.string.empty)
        calculator_result.text = resources.getString(R.string.empty)
    }

    fun numberAction(view: View) {
        when (view) {
            is Button -> {
                if (view.text == ".") {
                    if (decimalEnabled) {
                        calculator_operations.append(view.text)
                        decimalEnabled = false
                    }
                } else {
                    calculator_operations.append(view.text)
                }
                operationEnabled = true
            }
        }
    }

    fun operationAction(view: View) {
        when (view is Button && operationEnabled) {
            true -> {
                calculator_operations.append(view.text)
                operationEnabled = false
                decimalEnabled = true
            }
        }
    }

    fun equalsAction(view: View) {
        val operations = calculator_operations.text.toString()
        viewModel.calculateResults(operations)
    }

    fun backspaceAction(view: View) {
        when (calculator_operations.text.length) {
            0 -> Unit
            else -> calculator_operations.text = calculator_operations.text.dropLast(1)
        }
    }
}