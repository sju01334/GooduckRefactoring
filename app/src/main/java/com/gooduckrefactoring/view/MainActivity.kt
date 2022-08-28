package com.gooduckrefactoring.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivityMainBinding

class MainActivity() : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun setupEvents() {

    }

    override fun setValues() {
    }


}