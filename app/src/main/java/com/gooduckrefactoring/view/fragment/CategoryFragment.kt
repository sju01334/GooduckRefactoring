package com.kurly.kurlyapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.CategoryRecyclerviewAdapter
import com.gooduckrefactoring.adapter.productRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentCategoryBinding
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.CategoryViewModel
import com.gooduckrefactoring.viewmodel.CategoryViewModelFactory
import com.gooduckrefactoring.viewmodel.HomeViewModel
import com.gooduckrefactoring.viewmodel.HomeViewModelFactory

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    override val layoutId: Int =R.layout.fragment_category
    private lateinit var categoryAdapter: CategoryRecyclerviewAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, CategoryViewModelFactory())[CategoryViewModel::class.java]
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        binding.categoryRecyclerview.apply {
            categoryAdapter =  CategoryRecyclerviewAdapter(requireContext())
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.categoryItemList.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it)
        }

    }

    override fun init() {
    }
}