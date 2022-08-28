package com.gooduckrefactoring.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivityLoginBinding
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory
import com.gooduckrefactoring.viewmodel.UserViewModel
import com.gooduckrefactoring.viewmodel.UserViewModelFactory

class LoginActivity() : BaseActivity<ActivityLoginBinding>() {


    override val layoutId: Int = R.layout.activity_login
    private val viewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(application)).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            viewModel.normalLogin( binding.EmailEdt.text.toString(), binding.PWEdt.text.toString())
        }

    }

    override fun setValues() {
        viewModel.response.observe(binding.lifecycleOwner!!) {
//            Log.e("event handled tag", "writePostEvent before -> ${event.hasBeenHandled}")

            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, it.message , Toast.LENGTH_SHORT).show()
        }

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}