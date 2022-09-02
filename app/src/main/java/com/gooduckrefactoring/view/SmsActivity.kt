package com.gooduckrefactoring.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivitySmsBinding
import com.gooduckrefactoring.viewmodel.SmsViewModel
import com.gooduckrefactoring.viewmodel.SmsViewModelFactory

class SmsActivity : BaseActivity<ActivitySmsBinding>() {
    override val layoutId = R.layout.activity_sms
    private val smsViewModel by lazy {
        ViewModelProvider(this, SmsViewModelFactory(application))[SmsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = smsViewModel

        setupEvents()
        setValues()
        initAppbar()
    }
    override fun setupEvents() {

    }

    override fun setValues() {
    }

    override fun initAppbar() {
        titleTxt.text = "휴대폰 인증"
    }
}