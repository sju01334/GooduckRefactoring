package com.gooduckrefactoring.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.databinding.ActivityLoginBinding
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory
import com.navercorp.nid.NaverIdLoginSDK
import com.nepplus.gooduck.utils.ContextUtil

class LoginActivity() : BaseActivity<ActivityLoginBinding>() {


    override val layoutId: Int = R.layout.activity_login
    private val loginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(application)).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel

        NaverIdLoginSDK.initialize(
            this,
            getString(R.string.naver_client_id),
            getString(R.string.naverClientSecret) ,
            getString(R.string.naverClientName)
        )

        setupEvents()
        setValues()
        initAppbar()

    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            loginViewModel.normalLogin( binding.EmailEdt.text.toString(), binding.PWEdt.text.toString())
        }

        binding.kakaoLoginBtn.setOnClickListener {
            loginViewModel.kakaoLogin()
        }
        binding.naverLoginBtn.setOnClickListener {
            loginViewModel.naverLogin()
        }

    }

    override fun setValues() {
        loginViewModel.response.observe(binding.lifecycleOwner!!) {
            it.data?.let { user -> ContextUtil.setLoginToken(this, user.token) }
            RetrofitInstance.token = ContextUtil.getLoginToken(this)
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, it.message , Toast.LENGTH_SHORT).show()
            finish()
        }

        loginViewModel.errorMessage.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun initAppbar() {
        backBtn.isVisible= false
        bagBtn.isVisible = false
    }


}