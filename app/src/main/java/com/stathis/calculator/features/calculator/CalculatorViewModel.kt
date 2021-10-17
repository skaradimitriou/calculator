package com.stathis.calculator.features.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.exp

class CalculatorViewModel : ViewModel() {

    val result = MutableLiveData<String>()

    fun calculateResults(expression: String) {
        val commaExpression = seperateExpressionWithCommas(expression)

        when(commaExpression.isEmpty()){
            true -> result.value = ""
            false -> {
                //
            }
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