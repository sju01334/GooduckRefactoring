package com.kurly.kurlyapplication.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.RankRecyclerviewAdapter
import com.gooduckrefactoring.adapter.TagRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentSearchBinding
import com.gooduckrefactoring.view.MainActivity
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.HomeViewModel
import com.gooduckrefactoring.viewmodel.HomeViewModelFactory
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

    lateinit var tagAdapter : TagRecyclerviewAdapter
    lateinit var rankAdapter: RankRecyclerviewAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, HomeViewModelFactory())[HomeViewModel::class.java]
    }

    override fun init() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEvents()
        setValues()

        initRecyclerview()
    }

    override fun setupEvents() {

        binding.searchEdt.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                (requireActivity() as MainActivity).supportActionBar!!.hide()
                binding.backToSearch.isVisible = true

            }else{
                (requireActivity() as MainActivity).supportActionBar!!.show()
                binding.backToSearch.isVisible = false
            }
        }

        binding.backToSearch.setOnClickListener {
            (requireActivity() as MainActivity).supportActionBar!!.show()
            binding.backToSearch.isVisible = false
        }


    }

    override fun setValues() {
        viewModel.recommendItem.observe(viewLifecycleOwner) {
            tagAdapter.submitList(it)
        }

        viewModel.reviewItemList.observe(viewLifecycleOwner){
            rankAdapter.submitList(it)
        }

    }

    fun initRecyclerview(){
        binding.recommendRecyclerview.apply {
            tagAdapter = TagRecyclerviewAdapter("recommend")
            adapter = tagAdapter
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
        }

        binding.rankRecyclerview.apply {
            rankAdapter = RankRecyclerviewAdapter()
            adapter = rankAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


}