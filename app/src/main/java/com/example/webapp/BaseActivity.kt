package com.example.webapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    protected var isThisAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isThisAdmin = intent.getBooleanExtra("isThisAdmin", false)
        Toast.makeText(this, "YEs + $isThisAdmin", Toast.LENGTH_SHORT).show()
    }
}