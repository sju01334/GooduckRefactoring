package com.gooduckrefactoring.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ActivityLoginBinding
import com.gooduckrefactoring.databinding.ActivitySignupBinding

class SignupActivity() :BaseActivity<ActivitySignupBinding>() {
    override val layoutId: Int = R.layout.activity_signup

    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppbar()
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        navigationController.addOnDestinationChangedListener{ _ , destination, _ ->
            when(destination.id){
                R.id.signup_step_1 ->{

                }
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()

    }

    override fun setValues() {
        setSupportActionBar(binding.signupToolbar)
        setupActionBarWithNavController(navigationController)

    }

    override fun initAppbar() {
    }

}