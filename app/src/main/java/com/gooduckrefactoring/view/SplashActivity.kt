package com.gooduckrefactoring.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gooduckrefactoring.R

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}