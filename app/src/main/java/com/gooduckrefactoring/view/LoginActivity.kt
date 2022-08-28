package com.gooduckrefactoring.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            viewModel.userClicksOnButton(true, binding.EmailEdt.text.toString(), binding.PWEdt.text.toString())
        }

    }

    override fun setValues() {
        viewModel.navigateToDetails.observe(binding.lifecycleOwner!!) { event ->
            Log.e("event handled tag", "writePostEvent before -> ${event.hasBeenHandled}")

            if(viewModel.isLoginOk){
                event.getContentIfNotHandled()?.let {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
            }

            Log.e("event handled tag", "writePostEvent after -> ${event.hasBeenHandled}")

        }
    }
}