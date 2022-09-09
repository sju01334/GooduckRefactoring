package com.gooduckrefactoring.view.fragment.signupfragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.*
import com.gooduckrefactoring.view.SignupActivity
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.nepplus.gooduck.utils.AppUtil

class StepFourFragment : BaseFragment<FragmentStepFourBinding>() {

    override val layoutId: Int = R.layout.fragment_step_four


    private val loginViewModel by lazy {
        ViewModelProvider(requireActivity())[LoginViewModel::class.java]
    }

    override fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.confirmBtn.setOnClickListener {

        }

    }

    override fun setValues() {
        binding.editNick.requestFocus()
        AppUtil.showSoftInput(requireContext(), binding.editNick)
        binding.confirmBtn.isVisible = false

        loginViewModel.isNickDupli.observe(viewLifecycleOwner){
            if(it == true){
                if(binding.editNick.length() < 1){
                    binding.description.text = "닉네임을 알려주세요"
                }
            }
        }


    }


}