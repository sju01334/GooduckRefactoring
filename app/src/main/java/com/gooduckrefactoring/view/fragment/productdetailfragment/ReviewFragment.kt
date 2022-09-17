package com.gooduckrefactoring.view.fragment.productdetailfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentBestBinding
import com.gooduckrefactoring.databinding.FragmentDescriptionBinding
import com.gooduckrefactoring.databinding.FragmentReviewBinding
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.CategoryViewModel
import com.gooduckrefactoring.viewmodel.CategoryViewModelFactory

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {

    override val layoutId: Int = R.layout.fragment_review

    private val categoryViewModel: CategoryViewModel by activityViewModels()

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

        categoryViewModel.selectedCategoryItemList.observe(viewLifecycleOwner){
            it?.let {
                Log.d("ReviewFragment", it.toString())
            }

        }
    }

    fun initRecyclerView(){

    }


}