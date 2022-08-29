package com.gooduckrefactoring.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kurly.kurlyapplication.ui.fragment.homefragment.BestFragment
import com.kurly.kurlyapplication.ui.fragment.homefragment.CheapFragment
import com.kurly.kurlyapplication.ui.fragment.homefragment.NewFragment
import com.kurly.kurlyapplication.ui.fragment.homefragment.TodayFragment

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