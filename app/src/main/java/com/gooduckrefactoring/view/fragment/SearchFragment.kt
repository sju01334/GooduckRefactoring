package com.kurly.kurlyapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentSearchBinding
import com.gooduckrefactoring.view.fragment.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

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