package com.gooduckrefactoring.view.fragment.homefragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.BannerRecyclerviewAdapter
import com.gooduckrefactoring.adapter.ProductRecyclerviewAdapter
import com.gooduckrefactoring.databinding.FragmentTodayBinding
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.gooduckrefactoring.viewmodel.HomeViewModel
import com.gooduckrefactoring.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class TodayFragment : BaseFragment<FragmentTodayBinding>() {

    override val layoutId: Int = R.layout.fragment_today
    private lateinit var bannerViewPager: BannerRecyclerviewAdapter
    private lateinit var productAdapter: ProductRecyclerviewAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, HomeViewModelFactory())[HomeViewModel::class.java]
    }

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

    }

    override fun setValues() {
        viewModel.bannerItemList.observe(viewLifecycleOwner) {
            bannerViewPager.submitList(it)
            binding.totalBanner.text = it.size.toString()
        }

        viewModel.currentPosition.observe(viewLifecycleOwner) {
            binding.bannerViewpager.currentItem = it
        }

        viewModel.productItemList.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }



    }

    private fun autoScrollViewPager() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            while (viewLifecycleOwner.lifecycleScope.isActive) {
                delay(2000)
                viewModel.getCurrentPosition()?.let {
                    viewModel.setCurrentPosition(it.plus(1) % viewModel.getTotalBanner()!!)
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
                    viewModel.setCurrentPosition(position)
                }
            })
        }
    }

    private fun initProductRecyclerview(){
        binding.productRecyclerView.apply {
            productAdapter = ProductRecyclerviewAdapter()
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

}