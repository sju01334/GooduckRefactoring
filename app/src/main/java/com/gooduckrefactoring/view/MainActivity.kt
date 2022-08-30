package com.gooduckrefactoring.view

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.MainViewPagerAdapter
import com.gooduckrefactoring.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int = R.layout.activity_main
    private lateinit var mPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setupEvents()
        setValues()
        initAppbar()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mPagerAdapter = MainViewPagerAdapter(this)
        binding.mainViewPager.adapter = mPagerAdapter

        binding.mainViewPager.currentItem = 0
        binding.mainViewPager.isUserInputEnabled = false
        binding.bottomNav.selectedItemId = R.id.home

        binding.mainViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNav.menu.getItem(position).isChecked = true
                }
            })

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    binding.mainViewPager.currentItem = 0
                }
                R.id.category -> {
                    binding.mainViewPager.currentItem = 1
                }
                R.id.search -> {
                    binding.mainViewPager.currentItem = 2
                }
                R.id.myKurly -> {
                    binding.mainViewPager.currentItem = 3
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun initAppbar() {
        logo.isVisible = true
        bagBtn.isVisible = true
        titleTxt.isVisible = false
        backBtn.isVisible = false
    }


}