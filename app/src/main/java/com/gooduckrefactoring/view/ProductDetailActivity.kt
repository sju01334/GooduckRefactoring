package com.gooduckrefactoring.view

import android.os.Bundle
import com.bumptech.glide.Glide
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.HomeViewPagerAdapter
import com.gooduckrefactoring.adapter.ProductDetailViewPagerAdapter
import com.gooduckrefactoring.databinding.ActivityProductDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.nepplus.gooduck.models.Product

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    override val layoutId: Int = R.layout.activity_product_detail

    lateinit var mProduct : Product
    lateinit var mPagerAdapter : ProductDetailViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProduct = intent.getSerializableExtra("product") as Product


        setValues()
        setupEvents()
    }
    override fun setupEvents() {

    }

    override fun setValues() {
        Glide.with(this)
            .load(mProduct.imageUrl)
            .centerCrop()
            .into(binding.productImg)

        mPagerAdapter = ProductDetailViewPagerAdapter(this)
        binding.productDetailViewPager.adapter = mPagerAdapter

        TabLayoutMediator(binding.productDetailTabLayout, binding.productDetailViewPager){ tab, position ->
            when(position){
                0 -> tab.text = "상품설명"
                1 -> tab.text = "상세정보"
                2 -> tab.text = "후기"
                else -> tab.text = "문의"
            }
        }.attach()

    }

    override fun initAppbar() {
    }
}