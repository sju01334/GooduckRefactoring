package com.gooduckrefactoring.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.ProductFullRecyclerviewAdapter
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
import com.nepplus.gooduck.utils.AppUtil

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

    lateinit var tagAdapter : TagRecyclerviewAdapter
    lateinit var recentAdapter: TagRecyclerviewAdapter
    lateinit var rankAdapter: RankRecyclerviewAdapter
    lateinit var productFullAdapter: ProductFullRecyclerviewAdapter

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

        binding.searchedRecyclerview.bringToFront()

        setupEvents()
        setValues()

        initRecyclerview()
    }

    override fun setupEvents() {

        binding.searchEdt.setOnFocusChangeListener { _, hasFocus ->
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
            binding.searchedRecyclerview.isVisible = false
            binding.searchEdt.clearFocus()
            binding.searchEdt.text.clear()

        }

        binding.searchEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("%%", binding.searchEdt.text.toString())
                searchItem()

            }
            true
        }

        binding.searchEdt.addTextChangedListener(textWatcher)


    }

    private fun searchItem() {
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        binding.backToSearch.isVisible = true
        homeViewModel.searchProduct(binding.searchEdt.text.toString())
        binding.searchedRecyclerview.isVisible = true
        AppUtil.hideSoftInput(requireContext(), binding.searchEdt)
        //                binding.searchEdt.clearFocus()
        historyViewModel.addHistory(History(null, binding.searchEdt.text.toString()))
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

        homeViewModel.searchProduct.observe(viewLifecycleOwner){
            productFullAdapter.submitList(it)
        }

    }

    fun initRecyclerview(){
        binding.recommendRecyclerview.apply {
            tagAdapter = TagRecyclerviewAdapter("recommend"){
                binding.searchEdt.setText(it)
                searchItem()
            }
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
            recentAdapter = TagRecyclerviewAdapter("recent"){
                binding.searchEdt.setText(it)
                searchItem()
            }
            adapter = recentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.searchedRecyclerview.apply {
            productFullAdapter = ProductFullRecyclerviewAdapter()
            adapter = productFullAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

    }

    private  val textWatcher = object  : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.length!! <=0){
                binding.searchedRecyclerview.isVisible = false
            }

        }

    }


}