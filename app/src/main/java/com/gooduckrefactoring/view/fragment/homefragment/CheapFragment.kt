package com.kurly.kurlyapplication.ui.fragment.homefragment

import android.os.Bundle
import android.view.View
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentCheapBinding
import com.gooduckrefactoring.view.fragment.BaseFragment

class CheapFragment : BaseFragment<FragmentCheapBinding>() {

    override val layoutId: Int = R.layout.fragment_cheap

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