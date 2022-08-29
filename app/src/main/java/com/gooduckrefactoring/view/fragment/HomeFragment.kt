package com.kurly.kurlyapplication.ui.fragment

import android.os.Bundle
import android.view.View
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.HomeViewPagerAdapter
import com.gooduckrefactoring.databinding.FragmentHomeBinding
import com.gooduckrefactoring.view.fragment.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutId: Int = R.layout.fragment_home

    lateinit var mPagerAdapter : HomeViewPagerAdapter

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

        mPagerAdapter = HomeViewPagerAdapter(requireActivity())
        binding.tabLayoutViewPager.adapter = mPagerAdapter

//        ViewPager2 + TabLayout의 결합 코드
//        파라미터 ( tablayout의 변수 ,  viewpager2의 변수
        TabLayoutMediator(binding.homeTabLayout, binding.tabLayoutViewPager){ tab, position ->
            when(position){
                0 -> tab.text = "투데이"
                1 -> tab.text = "신상품"
                2 -> tab.text = "베스트"
                else -> tab.text = "세일"
            }
        }.attach()
    }


}