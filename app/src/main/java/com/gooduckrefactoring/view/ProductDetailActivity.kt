package com.gooduckrefactoring.view

import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.HomeViewPagerAdapter
import com.gooduckrefactoring.adapter.ProductDetailViewPagerAdapter
import com.gooduckrefactoring.databinding.ActivityProductDetailBinding
import com.gooduckrefactoring.util.AppBarStateChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nepplus.gooduck.models.Product
import java.text.DecimalFormat

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    override val layoutId: Int = R.layout.activity_product_detail

    lateinit var mProduct : Product
    private lateinit var mPagerAdapter : ProductDetailViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProduct = intent.getSerializableExtra("product") as Product

        initAdapter()
        setValues()
        setupEvents()
    }
    override fun setupEvents() {

        binding.appbar.addOnOffsetChangedListener(appBarStateChangeListener)

    }

    override fun setValues() {
        Glide.with(this)
            .load(mProduct.imageUrl)
            .centerCrop()
            .into(binding.productImg)

        binding.productName.text =  "[미진 브랜드] 미진${ mProduct.name }"
        binding.productName2.text = "[미진 브랜드] 미진${ mProduct.name }"

        val dec = DecimalFormat("#,###")
        binding.price.text = "${ dec.format(mProduct.price) }원"





    }

    private fun initAdapter() {
        mPagerAdapter = ProductDetailViewPagerAdapter(this)
        binding.productDetailViewPager.adapter = mPagerAdapter

        TabLayoutMediator(binding.productDetailTabLayout, binding.productDetailViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "상품설명"
                1 -> tab.text = "상세정보"
                2 -> tab.text = "후기"
                else -> tab.text = "문의"
            }
        }.attach()
    }

    override fun initAppbar() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private val appBarStateChangeListener: AppBarStateChangeListener =
        object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED -> {binding.productName.isVisible = false}
                    State.COLLAPSED -> {binding.productName.isVisible = true}
                    State.IDLE -> {binding.productName.isVisible = false}
                }
            }
        }


}