package com.gooduckrefactoring.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivityLoginBinding
import com.gooduckrefactoring.network.RetrofitInstance
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity() : BaseActivity<ActivityLoginBinding>() {

    override val layoutId: Int = R.layout.activity_login
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private val loginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(application))[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel

        NaverIdLoginSDK.initialize(
            this,
            getString(R.string.naver_client_id),
            getString(R.string.naverClientSecret),
            getString(R.string.naverClientName)
        )

        setupEvents()
        setValues()
        initAppbar()
        initGoogleLauncher()

    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            loginViewModel.normalLogin(binding.EmailEdt.text.toString(), binding.PWEdt.text.toString())
        }

        binding.kakaoLoginBtn.setOnClickListener {
            loginViewModel.kakaoLogin()
        }
        binding.naverLoginBtn.setOnClickListener {
            loginViewModel.naverLogin()
        }

        binding.googleLoginBtn.setOnClickListener {
            googleLogin()
        }

        binding.signupBtn.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java)) }

    }

    override fun setValues() {
        loginViewModel.response.observe(binding.lifecycleOwner!!) {
            it.data?.let { user -> ContextUtil.setLoginToken(this, user.token) }
            RetrofitInstance.token = ContextUtil.getLoginToken(this)
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            finish()
        }

        loginViewModel.errorMessage.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun initAppbar() {
        backBtn.isVisible = false
        bagBtn.isVisible = false
        cartCnt.isVisible = false
    }

    fun googleLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
            val signInIntent: Intent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private fun initGoogleLauncher() {
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                loginViewModel.googleLogin(task)
            }
        }
    }









}