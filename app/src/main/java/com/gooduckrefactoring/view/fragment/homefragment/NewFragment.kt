package com.gooduckrefactoring.view.fragment.homefragment

import android.os.Bundle
import android.view.View
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentNewBinding
import com.gooduckrefactoring.view.fragment.BaseFragment

class NewFragment : BaseFragment<FragmentNewBinding>() {


    override val layoutId: Int = R.layout.fragment_new

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