package com.stathis.calculator.features.calculator

import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
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
            val amount = when(calculator_result.text.toString().isNullOrEmpty()){
                true -> "0.0"
                else -> calculator_result.text.toString()
            }

            when(amount.isNullOrEmpty()) {
                true -> notifyUser()
                false -> goToCurrencyScreen(amount)
            }
        }

        viewModel.result.observe(this, Observer {
            calculator_result.text = it
        })
    }

    private fun goToCurrencyScreen(amount : String) {
        startActivity(Intent(this, CurrenciesActivity::class.java).also {
            it.putExtra("AMOUNT", amount)
        })
    }

    private fun notifyUser() {
        Snackbar.make(findViewById(R.id.calculator_parent_screen), resources.getString(R.string.enter_correct_amount), Snackbar.LENGTH_SHORT).show()
    }

    override fun stopOps() {
        viewModel.result.removeObservers(this)
    }

    fun allClearAction(view: View) {
        calculator_operations.text = resources.getString(R.string.empty)
        calculator_result.text = resources.getString(R.string.empty)
    }

    fun numberAction(view: View) {
        when (view is Button) {
            true -> {
                if (view.text == ".") {
                    if (decimalEnabled) {
                        calculator_result.append(view.text)
                        decimalEnabled = false
                    }
                } else {
                    calculator_result.append(view.text)
                }
                operationEnabled = true
            }
        }
    }

    fun operationAction(view: View) {
        when (view is Button && operationEnabled) {
            true -> {
                if(calculator_result.text.isNotEmpty()){
                    calculator_result.append(view.text)
                    operationEnabled = false
                    decimalEnabled = true
                } else {
                    when (calculator_result.text.contains('X') ||
                            calculator_result.text.contains('/') ||
                            calculator_result.text.contains('-') ||
                            calculator_result.text.contains('+')) {
                        true -> Unit
                        false -> {
                            calculator_result.append(view.text)
                            operationEnabled = false
                            decimalEnabled = true
                        }
                    }
                }
            }
        }
    }

    fun equalsAction(view: View) {
        val operations = calculator_result.text.toString()
        calculator_operations.text = operations
        calculator_result.text = ""
        viewModel.calculateResults(operations)
    }

    fun backspaceAction(view: View) {
        when (calculator_result.text.length) {
            0 -> Unit
            else -> calculator_result.text = calculator_result.text.dropLast(1)
        }
    }
}