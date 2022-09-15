package com.gooduckrefactoring.view.fragment.homefragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.BannerRecyclerviewAdapter
import com.gooduckrefactoring.adapter.ProductHorizonRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentTodayBinding
import com.gooduckrefactoring.view.ProductDetailActivity
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.CartViewModel
import com.gooduckrefactoring.viewmodel.ProductViewModel
import com.gooduckrefactoring.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class TodayFragment : BaseFragment<FragmentTodayBinding>() {

    override val layoutId: Int = R.layout.fragment_today
    private lateinit var bannerViewPager: BannerRecyclerviewAdapter
    private lateinit var productAdapter: ProductHorizonRecyclerviewAdapter

    private val HomeViewModel by lazy {
        ViewModelProvider(this, ProductViewModelFactory())[ProductViewModel::class.java]
    }

    /*before (안되는거)
    private val cartViewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory())[CartViewModel::class.java]
    }*/

    // after (되는거)
    private val cartViewModel by lazy {
        ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    // after 2 (이것도 됩니다)
//    private val cartViewModel: CartViewModel by activityViewModels()

    override fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager2()
        initProductRecyclerview()
        setupEvents()
        setValues()
        autoScrollViewPager()
    }

    override fun setupEvents() {

        productAdapter.onClickCart = {
            cartViewModel.addToCartItem(it.id)
//            Toast.makeText(this@ProductsActivity, "${it.name}을 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
        }

        cartViewModel.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }


        cartViewModel.successFlag.observe(viewLifecycleOwner){
            cartViewModel.cartItemList.value?.let {
                Toast.makeText(requireContext(),
                    "${cartViewModel.cartItemList.value!!.last().product.name} 을 장바구니에 담았습니다",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun setValues() {
        HomeViewModel.bannerItemList.observe(viewLifecycleOwner) {
            bannerViewPager.submitList(it)
            binding.totalBanner.text = it.size.toString()
        }

        HomeViewModel.currentPosition.observe(viewLifecycleOwner) {
            binding.bannerViewpager.currentItem = it
        }

        HomeViewModel.productItemList.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }


    }

    private fun autoScrollViewPager() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            while (viewLifecycleOwner.lifecycleScope.isActive) {
                delay(2000)
                HomeViewModel.getCurrentPosition()?.let {
                    HomeViewModel.setCurrentPosition(it.plus(1) % HomeViewModel.getTotalBanner()!!)
                }
            }
        }
    }

    private fun initViewPager2() {
        binding.bannerViewpager.apply {
            bannerViewPager = BannerRecyclerviewAdapter()
            adapter = bannerViewPager
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.currentBanner.text = "${position + 1}"
                    HomeViewModel.setCurrentPosition(position)
                }
            })
        }
    }

    private fun initProductRecyclerview() {
        binding.productRecyclerView.apply {
            productAdapter = ProductHorizonRecyclerviewAdapter() {
                val myIntent = Intent(requireContext(), ProductDetailActivity::class.java)
                myIntent.putExtra("product", it)
                startActivity(myIntent)
            }
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

}