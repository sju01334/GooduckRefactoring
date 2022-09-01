package com.gooduckrefactoring.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.databinding.ActivityLoginBinding
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory
import com.gooduckrefactoring.viewmodel.UserViewModel
import com.gooduckrefactoring.viewmodel.UserViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.nepplus.gooduck.utils.ContextUtil

class LoginActivity() : BaseActivity<ActivityLoginBinding>() {


    override val layoutId: Int = R.layout.activity_login
    private val viewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(application)).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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
            viewModel.normalLogin( binding.EmailEdt.text.toString(), binding.PWEdt.text.toString())
        }

        binding.kakaoLoginBtn.setOnClickListener {
            viewModel.kakaoLogin()
        }
        binding.naverLoginBtn.setOnClickListener {
            viewModel.naverLogin()
        }

    }

    override fun setValues() {
        viewModel.response.observe(binding.lifecycleOwner!!) {
            it.data?.let { user -> ContextUtil.setLoginToken(this, user.token) }
            RetrofitInstance.token = ContextUtil.getLoginToken(this)
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, it.message , Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.errorMessage.observe(this, Observer {
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