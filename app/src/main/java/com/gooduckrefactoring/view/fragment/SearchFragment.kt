package com.kurly.kurlyapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentSearchBinding
import com.gooduckrefactoring.view.MainActivity
import com.gooduckrefactoring.view.fragment.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

    override fun init() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEvents()
        setValues()
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


    }

    override fun setValues() {

    }

    fun initrecommandLayout(){
        val stringList = listOf("스팸", "호빵", "왕란", "콘푸로스트", "딸기")


    }


}