package com.stathis.calculator.abstraction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class SimplifiedActivity(layoutId : Int) : AppCompatActivity(layoutId) {

    abstract fun init()
    abstract fun startOps()
    abstract fun stopOps()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPostResume() {
        super.onPostResume()
        startOps()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        stopOps()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}