package com.gooduckrefactoring.view.fragment.signupfragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentBestBinding
import com.gooduckrefactoring.databinding.FragmentStepOneBinding
import com.gooduckrefactoring.databinding.FragmentStepTwoBinding
import com.gooduckrefactoring.view.SignupActivity
import com.gooduckrefactoring.view.fragment.BaseFragment

class StepTwoFragment : BaseFragment<FragmentStepTwoBinding>() {

    override val layoutId: Int = R.layout.fragment_step_two

    val args: StepTwoFragmentArgs by navArgs()

    override fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        binding.txt.text = args.email
    }


}