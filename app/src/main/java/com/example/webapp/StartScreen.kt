package com.example.webapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class StartScreen : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLoginLoginAsAdminButton.setOnClickListener(this)
        activityLoginLoginAsUserButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            activityLoginLoginAsUserButton -> {
                NavigationUtils().moveToMainListActivity(this, false)
            }
            activityLoginLoginAsAdminButton -> {
                NavigationUtils().moveToMainListActivity(this, true)
            }
        }
    }
}