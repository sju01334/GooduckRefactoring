package com.gooduckrefactoring.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
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
                    with(binding){
                        currentStep.text = "1"
                        backBtn.isVisible = true
                    }
                }
                R.id.signup_step_2 ->{
                    with(binding){
                        currentStep.text = "2"
                        backBtn.isVisible = false
                    }
                }
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()

    }

    override fun setValues() {
        setSupportActionBar(binding.signupToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navigationController)

    }

    override fun initAppbar() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

}