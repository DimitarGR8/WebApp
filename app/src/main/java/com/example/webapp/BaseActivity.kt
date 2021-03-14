package com.example.webapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    companion object {
        const val RC_CAPTURE_IMAGE = 100
        const val RC_PERMISSION = 200
    }

    protected var isThisAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isThisAdmin = intent.getBooleanExtra("isThisAdmin", false)
        Toast.makeText(this, "YEs + $isThisAdmin", Toast.LENGTH_SHORT).show()
    }
}