package com.gooduckrefactoring.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.CategoryRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentCategoryBinding
import com.gooduckrefactoring.view.ProductsActivity
import com.gooduckrefactoring.viewmodel.CategoryViewModel
import com.gooduckrefactoring.viewmodel.CategoryViewModelFactory
import com.nepplus.gooduck.models.Category

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    override val layoutId: Int =R.layout.fragment_category
    private lateinit var categoryAdapter: CategoryRecyclerviewAdapter



    private val viewModel by lazy {
        ViewModelProvider(this, CategoryViewModelFactory())[CategoryViewModel::class.java]
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setValues()
        setupEvents()
    }

    override fun setupEvents() {
        categoryAdapter.onClickItem = {
            val myIntent = Intent(requireContext(), ProductsActivity::class.java)
            myIntent.putExtra("selected", it)
            startActivity(myIntent)
//            Toast.makeText(requireContext(), it.id.toString(), Toast.LENGTH_SHORT).show()
        }


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