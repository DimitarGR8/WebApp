package com.example.webapp.activities.startScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.webapp.R
import com.example.webapp.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_main.*

class StartScreenActivity : AppCompatActivity(), View.OnClickListener {

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