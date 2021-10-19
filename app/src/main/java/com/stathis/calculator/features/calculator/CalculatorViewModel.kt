package com.stathis.calculator.features.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    val result = MutableLiveData<String>()

    fun calculateResults(expression: String) {
        val commaExpression = seperateExpressionWithCommas(expression)

        when(commaExpression.isEmpty()){
            true -> result.value = ""
            false -> {
                val first = commaExpression.first().toString().toDouble()
                val operation = commaExpression[commaExpression.size / 2]
                val last = commaExpression.last().toString().toDouble()

                var resultOperation = when(operation){
                    'X' -> first / last
                    '/' -> first / last
                    '-' -> first - last
                    '+' -> first + last
                    else -> first
                }

                bindResult(resultOperation.toString())
            }
        }
    }

    private fun bindResult(operation: String) {
        when(operation.substringAfterLast(".")){
            "0" -> result.value = operation.substringBeforeLast(".")
            else -> result.value = operation
        }
    }

    private fun seperateExpressionWithCommas(expression: String): MutableList<Any> {
        val list = mutableListOf<Any>()

        when (expression.length) {
            0 -> Unit
            else -> {
                var currentDigit = ""

                for(character in expression){
                    if (character.isDigit() || character == '.'){
                        currentDigit += character
                    } else {
                        list.add(currentDigit.toFloat())
                        currentDigit = ""
                        list.add(character)
                    }
                }

                if(currentDigit != "") list.add(currentDigit)
            }
        }

        return list
    }
}