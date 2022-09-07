package com.gooduckrefactoring.view.fragment.signupfragment

import android.os.Bundle
import android.view.View
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentBestBinding
import com.gooduckrefactoring.databinding.FragmentStepOneBinding
import com.gooduckrefactoring.databinding.FragmentStepTwoBinding
import com.gooduckrefactoring.view.fragment.BaseFragment

class StepTwoFragment : BaseFragment<FragmentStepTwoBinding>() {

    override val layoutId: Int = R.layout.fragment_step_two

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
    }


}