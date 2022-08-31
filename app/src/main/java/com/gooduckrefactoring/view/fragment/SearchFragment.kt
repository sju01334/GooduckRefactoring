package com.gooduckrefactoring.view.fragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.RankRecyclerviewAdapter
import com.gooduckrefactoring.adapter.TagRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentSearchBinding
import com.gooduckrefactoring.dto.History
import com.gooduckrefactoring.view.MainActivity
import com.gooduckrefactoring.viewmodel.HistoryViewModel
import com.gooduckrefactoring.viewmodel.HistoryViewModelFactory
import com.gooduckrefactoring.viewmodel.HomeViewModel
import com.gooduckrefactoring.viewmodel.HomeViewModelFactory
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

    lateinit var tagAdapter : TagRecyclerviewAdapter
    lateinit var recentAdapter: TagRecyclerviewAdapter
    lateinit var rankAdapter: RankRecyclerviewAdapter

    private val homeViewModel by lazy {
        ViewModelProvider(this, HomeViewModelFactory())[HomeViewModel::class.java]
    }

    private val historyViewModel by lazy {
        ViewModelProvider(this, HistoryViewModelFactory(requireActivity()!!.application))[HistoryViewModel::class.java]
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

        binding.searchEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Toast.makeText(requireContext(), "눌림림", Toast.LENGTH_SHORT).show()
                historyViewModel.addHistory(History(null, binding.searchEdt.text.toString()))
            }
            true
        }




    }

    override fun setValues() {
        homeViewModel.recommendItem.observe(viewLifecycleOwner) {
            tagAdapter.submitList(it)
        }

        homeViewModel.reviewItemList.observe(viewLifecycleOwner){
            rankAdapter.submitList(it)
        }

        historyViewModel.allData.observe(viewLifecycleOwner){
            recentAdapter.submitList(it.map { it.keyword }.take(10))
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

        binding.recentRecyclerview.apply {
            recentAdapter = TagRecyclerviewAdapter("recent")
            adapter = recentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }


}