package com.gooduckrefactoring.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

    abstract class BaseFragment<T : ViewBinding> : Fragment() {

    lateinit var viewDataBinding : T
    abstract val layoutId : Int

    abstract fun init()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false )

        init()

        return viewDataBinding.root
    }
}