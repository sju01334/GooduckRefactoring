package com.gooduckrefactoring.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kurly.kurlyapplication.ui.fragment.CategoryFragment
import com.kurly.kurlyapplication.ui.fragment.HomeFragment
import com.kurly.kurlyapplication.ui.fragment.MyGooduckFragment
import com.kurly.kurlyapplication.ui.fragment.SearchFragment

class MainViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> CategoryFragment()
            2 -> SearchFragment()
            else -> MyGooduckFragment()
        }
    }


}