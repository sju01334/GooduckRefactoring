package com.gooduckrefactoring.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivityMainBinding
import com.nepplus.gooduck.utils.ContextUtil

class MainActivity() : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        binding.txt.text = ContextUtil.getLoginToken(mContext)
    }


}