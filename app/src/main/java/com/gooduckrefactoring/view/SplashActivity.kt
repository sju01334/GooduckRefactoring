package com.gooduckrefactoring.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.databinding.ActivitySplashBinding
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.viewmodel.UserViewModel
import com.gooduckrefactoring.viewmodel.UserViewModelFactory
import com.kakao.sdk.common.util.Utility
import com.nepplus.gooduck.utils.ContextUtil
import com.nepplus.gooduck.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int = R.layout.activity_splash

    var isTokenOk = false

    private val viewModel by lazy {
        ViewModelProvider(this, UserViewModelFactory(application))[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        viewModel.user.observe(this, Observer { user ->
            user ?: let {
                isTokenOk = true
            }
        })

    }

    override fun setValues() {

        var myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent : Intent
            if(isTokenOk){
                Toast.makeText(mContext, "${GlobalData.loginUser!!.nickname}님 환영합니다", Toast.LENGTH_SHORT).show()

                myIntent = Intent(mContext, MainActivity::class.java)

            }else{
                myIntent = Intent(mContext, LoginActivity::class.java)
            }
            startActivity(myIntent)
            finish()

        },2500)

    }

    fun getKeyhash(){
        var keyHash = Utility.getKeyHash(mContext)

        Log.d("kakao_keyHash", keyHash)
    }

    override fun initAppbar() {

    }
}