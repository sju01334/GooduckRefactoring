package com.gooduckrefactoring.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.ProductFullRecyclerviewAdapter
import com.gooduckrefactoring.adapter.RankRecyclerviewAdapter
import com.gooduckrefactoring.adapter.TagRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentSearchBinding
import com.gooduckrefactoring.dto.History
import com.gooduckrefactoring.util.MyItemDecoration
import com.gooduckrefactoring.view.MainActivity
import com.gooduckrefactoring.view.ProductDetailActivity
import com.gooduckrefactoring.viewmodel.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.nepplus.gooduck.models.Product
import com.nepplus.gooduck.utils.AppUtil

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

    lateinit var tagAdapter : TagRecyclerviewAdapter
    lateinit var recentAdapter: TagRecyclerviewAdapter
    lateinit var rankAdapter: RankRecyclerviewAdapter
    lateinit var productFullAdapter: ProductFullRecyclerviewAdapter

    private val homeViewModel by lazy {
        ViewModelProvider(this, ProductViewModelFactory())[ProductViewModel::class.java]
    }

    private val historyViewModel by lazy {
        ViewModelProvider(this, HistoryViewModelFactory(requireActivity().application))[HistoryViewModel::class.java]
    }

    private val cartViewModel by lazy {
        ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    private val reviewViewModel by lazy {
        ViewModelProvider(this, ReviewViewModelFactory())[ReviewViewModel::class.java]
    }

    override fun init() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchedRecyclerview.bringToFront()

        initRecyclerview()
        setupEvents()
        setValues()

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

        //imeOption ?????? ?????????
        binding.searchEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("%%", binding.searchEdt.text.toString())
                searchItem(binding.searchEdt.text.toString())

            }
            true
        }

        binding.searchEdt.addTextChangedListener(textWatcher)

        binding.eraseAll.setOnClickListener {
            historyViewModel.deleteAll()
            binding.recentLayout.isVisible = false
        }

        productFullAdapter.onClickCart = {
            cartViewModel.addToCartItem(it.id)
        }


    }



    override fun setValues() {
        homeViewModel.recommendItem.observe(viewLifecycleOwner) {
            tagAdapter.submitList(it)
        }

        reviewViewModel.reviewItemList.observe(viewLifecycleOwner){
            rankAdapter.submitList(it)
        }

        historyViewModel.allData.observe(viewLifecycleOwner){
            recentAdapter.submitList(it.map { it.keyword }.take(10))
            (it.isNotEmpty()).also { binding.recentLayout.isVisible = it }
        }

        cartViewModel.errorMsg.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }


        cartViewModel.successFlag.observe(viewLifecycleOwner){
            cartViewModel.cartItemList.value?.let {
                Toast.makeText(requireContext(),
                    "${cartViewModel.cartItemList.value!!.last().product.name} ??? ??????????????? ???????????????",
                    Toast.LENGTH_SHORT).show()
            }
        }


    }

    //?????????????????? ?????????
    fun initRecyclerview(){
        binding.recommendRecyclerview.apply {
            tagAdapter = TagRecyclerviewAdapter("recommend"){
                //?????? ????????? ?????? ?????????
                binding.searchEdt.setText(it)
                searchItem(it)
            }
            adapter = tagAdapter
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
        }

        binding.rankRecyclerview.apply {
            rankAdapter = RankRecyclerviewAdapter{
                binding.searchEdt.setText(it.product.name)
                searchItem(it.product.name)
                Log.d("%%%", it.product.name)
            }
            adapter = rankAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.recentRecyclerview.apply {
            recentAdapter = TagRecyclerviewAdapter("recent"){
                //?????? ?????????
                binding.searchEdt.setText(it)
                searchItem(it)
            }
            adapter = recentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.searchedRecyclerview.apply {
            productFullAdapter = ProductFullRecyclerviewAdapter(){
                val myIntent = Intent(requireContext(), ProductDetailActivity::class.java)
                myIntent.putExtra("product", it)
                startActivity(myIntent)
            }
            adapter = productFullAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(MyItemDecoration(2, 16, false))
        }

    }

    private fun searchItem(name : String) {

        val searchList  = mutableListOf<Product>()
        val productList = homeViewModel.productItemListAll.value!!
        for(i in productList.indices){
            if(productList[i].name == name){
                searchList.add(productList[i])
            }
            productFullAdapter.submitList(searchList)
        }
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        binding.backToSearch.isVisible = true
        binding.searchedRecyclerview.isVisible = true
        AppUtil.hideSoftInput(requireContext(), binding.searchEdt)
        //                binding.searchEdt.clearFocus()
        historyViewModel.addHistory(History(null, name))
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