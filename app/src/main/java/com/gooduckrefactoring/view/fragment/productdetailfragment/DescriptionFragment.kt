package com.gooduckrefactoring.view.fragment.productdetailfragment

import android.os.Bundle
import android.view.View
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentBestBinding
import com.gooduckrefactoring.databinding.FragmentDescriptionBinding
import com.gooduckrefactoring.view.fragment.BaseFragment

class DescriptionFragment : BaseFragment<FragmentDescriptionBinding>() {

    override val layoutId: Int = R.layout.fragment_description

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