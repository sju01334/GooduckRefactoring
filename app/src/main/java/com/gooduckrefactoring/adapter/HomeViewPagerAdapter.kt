package com.gooduckrefactoring.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gooduckrefactoring.view.fragment.homefragment.BestFragment
import com.gooduckrefactoring.view.fragment.homefragment.CheapFragment
import com.gooduckrefactoring.view.fragment.homefragment.NewFragment
import com.gooduckrefactoring.view.fragment.homefragment.TodayFragment

class HomeViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TodayFragment()
            1 -> NewFragment()
            2 -> BestFragment()
            else -> CheapFragment()
        }
    }


}