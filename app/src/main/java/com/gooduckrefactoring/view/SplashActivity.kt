package com.gooduckrefactoring.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivitySplashBinding
import com.gooduckrefactoring.viewmodel.UserViewModel
import com.gooduckrefactoring.viewmodel.UserViewModelFactory
import com.kakao.sdk.common.util.Utility
import com.nepplus.gooduck.utils.GlobalData

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding
    val layoutId: Int = R.layout.activity_splash

    var isTokenOk = false

    private val userViewModel by lazy {
        ViewModelProvider(this, UserViewModelFactory(application))[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        setupEvents()
        setValues()
    }

    fun setupEvents() {

        userViewModel.user.observe(this) { user ->
            if (user != null) {
                isTokenOk = true
                GlobalData.loginUser = user
            }
        }

    }

    fun setValues() {

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            Log.d("ttt", isTokenOk.toString())

            val myIntent : Intent
            if(isTokenOk){
                Toast.makeText(this, "${GlobalData.loginUser!!.nickname}님 환영합니다", Toast.LENGTH_SHORT).show()
                myIntent = Intent(this, MainActivity::class.java)

            }else{
                myIntent = Intent(this, LoginActivity::class.java)
            }
            startActivity(myIntent)
            finish()

        },2500)

    }

    fun getKeyhash(){
        val keyHash = Utility.getKeyHash(this)

        Log.d("kakao_keyHash", keyHash)
    }

}