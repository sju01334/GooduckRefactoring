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
import com.gooduckrefactoring.databinding.FragmentBestBinding
import com.gooduckrefactoring.databinding.FragmentStepOneBinding
import com.gooduckrefactoring.databinding.FragmentStepThreeBinding
import com.gooduckrefactoring.databinding.FragmentStepTwoBinding
import com.gooduckrefactoring.view.SignupActivity
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.nepplus.gooduck.utils.AppUtil

class StepThreeFragment : BaseFragment<FragmentStepThreeBinding>() {

    override val layoutId: Int = R.layout.fragment_step_three


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
            val action = StepThreeFragmentDirections.actionSignupStep3ToSignupStep4()
            Navigation.findNavController(requireView()).navigate(action)
        }

    }

    override fun setValues() {
        binding.editPw.requestFocus()
        AppUtil.showSoftInput(requireContext(), binding.editPw)
        binding.confirmBtn.isVisible = false
    }


}