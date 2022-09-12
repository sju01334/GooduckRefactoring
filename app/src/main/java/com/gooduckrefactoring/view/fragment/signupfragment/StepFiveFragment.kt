package com.gooduckrefactoring.view.fragment.signupfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.*
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.nepplus.gooduck.utils.AppUtil

class StepFiveFragment : BaseFragment<FragmentStepFiveBinding>() {

    override val layoutId: Int = R.layout.fragment_step_five


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
            loginViewModel.signInData.phone = binding.editPhone.text.toString()

            loginViewModel.signInToServer()


        }

    }

    override fun setValues() {
        binding.editPhone.requestFocus()
        AppUtil.showSoftInput(requireContext(), binding.editPhone)
        binding.confirmBtn.isVisible = false

        loginViewModel.signInResponse.observe(viewLifecycleOwner){
            Log.d("회원가입 정보", it.toString())
            Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }


}