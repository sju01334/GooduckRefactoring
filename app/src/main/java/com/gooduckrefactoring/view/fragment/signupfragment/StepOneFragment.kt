package com.gooduckrefactoring.view.fragment.signupfragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentStepOneBinding
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory
import com.nepplus.gooduck.utils.AppUtil

class StepOneFragment : BaseFragment<FragmentStepOneBinding>() {

    override val layoutId: Int = R.layout.fragment_step_one

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

        binding.loginBtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.confirmBtn.setOnClickListener {
            val email = binding.editId.text.toString()
            loginViewModel.signInData.email = email
            val action = StepOneFragmentDirections.actionSignupStep1ToSignupStep2(email)
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    override fun setValues() {
        binding.editId.requestFocus()

        loginViewModel.isEmailDupli.observe(viewLifecycleOwner) {

            if (it == false) {
                AppUtil.hideSoftInput(requireContext(), binding.editId)
                binding.confirmBtn.isVisible = false
                binding.editId.clearFocus()
                binding.loginBtn.isVisible = true
            } else {
                binding.loginBtn.isVisible = false
                AppUtil.showSoftInput(requireContext(), binding.editId)
                binding.confirmBtn.isVisible = binding.editId.length() > 1
            }

        }

    }


}