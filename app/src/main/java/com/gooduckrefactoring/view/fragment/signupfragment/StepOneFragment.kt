package com.gooduckrefactoring.view.fragment.signupfragment

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentBestBinding
import com.gooduckrefactoring.databinding.FragmentStepOneBinding
import com.gooduckrefactoring.view.LoginActivity
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.CategoryViewModel
import com.gooduckrefactoring.viewmodel.CategoryViewModelFactory
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory
import com.nepplus.gooduck.utils.AppUtil

class StepOneFragment : BaseFragment<FragmentStepOneBinding>() {

    override val layoutId: Int = R.layout.fragment_step_one

    private val loginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(requireActivity().application))[LoginViewModel::class.java]
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

        binding.loginBtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.confirmBtn.setOnClickListener {
            if(loginViewModel.isEmailDupli.value == false){
                //다음으로 넘어갈 수 있는 상태
                val email = binding.editId.text.toString()
                val action = StepOneFragmentDirections.actionSignupStep1ToSignupStep2()
                findNavController()
            }
        }
    }

    override fun setValues() {
         binding.editId.requestFocus()

        loginViewModel.isEmailDupli.observe(viewLifecycleOwner){

            if(it == false){
                AppUtil.hideSoftInput(requireContext(), binding.editId)
                binding.confirmBtn.isVisible = false
                binding.editId.clearFocus()
                binding.loginBtn.isVisible = true
            }else{
                binding.loginBtn.isVisible = false
                AppUtil.showSoftInput(requireContext(), binding.editId)
                binding.confirmBtn.isVisible = binding.editId.length() > 1
            }

        }

    }


}