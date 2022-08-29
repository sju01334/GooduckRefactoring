package com.kurly.kurlyapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentCategoryBinding
import com.gooduckrefactoring.view.fragment.BaseFragment

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    override val layoutId: Int =R.layout.fragment_category

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
    }

    override fun init() {
    }
}