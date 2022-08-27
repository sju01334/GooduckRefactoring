package com.gooduckrefactoring.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gooduckrefactoring.R

class LoginActivity : AppCompatActivity() {

//    viewModelFactory = ItemViewModelFactory(repository = TemplateRepository(mPost.templateId.toString()))
//    viewModel = ViewModelProvider(this, viewModelFactory)[ItemViewModel::class.java]
//    private val viewModel by lazy { ViewModelProvider(this,MainViewModel.Factory(application))[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}