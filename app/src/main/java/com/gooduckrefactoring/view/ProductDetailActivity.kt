package com.gooduckrefactoring.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.HomeViewPagerAdapter
import com.gooduckrefactoring.adapter.ProductDetailViewPagerAdapter
import com.gooduckrefactoring.databinding.ActivityProductDetailBinding
import com.gooduckrefactoring.dialog.CustomBottomDialog
import com.gooduckrefactoring.util.AppBarStateChangeListener
import com.gooduckrefactoring.viewmodel.CartViewModel
import com.gooduckrefactoring.viewmodel.CartViewModelFactory
import com.gooduckrefactoring.viewmodel.CategoryViewModel
import com.gooduckrefactoring.viewmodel.CategoryViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nepplus.gooduck.models.Product
import java.text.DecimalFormat

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    override val layoutId: Int = R.layout.activity_product_detail

    lateinit var mProduct : Product
    private lateinit var mPagerAdapter : ProductDetailViewPagerAdapter
    var likeFlag = false

    private val cartViewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory())[CartViewModel::class.java]
    }

    private val categoryViewModel by lazy {
        ViewModelProvider(this, CategoryViewModelFactory())[CategoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProduct = intent.getSerializableExtra("product") as Product
        Log.d("productActivity", mProduct.smallCategoryId.toString())
        categoryViewModel.getSelectedCategoryItems(mProduct.smallCategoryId)

        initAdapter()
        setValues()
        setupEvents()
        initObserve()
    }
    override fun setupEvents() {

        binding.appbar.addOnOffsetChangedListener(appBarStateChangeListener)
        binding.backBtn.setOnClickListener { finish() }
        binding.addCart.setOnClickListener {
            val customDialog = CustomBottomDialog(mProduct) {
                cartViewModel.addToCartItem(mProduct.id)
            }
            customDialog.show(supportFragmentManager, customDialog.mTag)
        }
        binding.likeBtn.setOnClickListener {
            likeFlag = !likeFlag
            if(likeFlag){
                binding.likeBtn.setImageDrawable(getDrawable(R.drawable.ic_heart_fill))
            }else{
                binding.likeBtn.setImageDrawable(getDrawable(R.drawable.ic_heart))
            }
        }

    }

    private fun initObserve() {
        cartViewModel.errorMsg.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        cartViewModel.successFlag.observe(this) {
            cartViewModel.cartItemList.value?.let {
                Toast.makeText(this,
                    "${cartViewModel.cartItemList.value!!.last().product.name} ??? ??????????????? ???????????????",
                    Toast.LENGTH_SHORT).show()
            }
        }


        cartViewModel.cartItemList.observe(this) {
            binding.cartCnt.text = it.size.toString()
        }
    }

    override fun setValues() {
        Glide.with(this)
            .load(mProduct.imageUrl)
            .centerCrop()
            .into(binding.productImg)

        binding.productName.text =  "[?????? ?????????] ??????${ mProduct.name }"
        binding.productName2.text = "[?????? ?????????] ??????${ mProduct.name }"
        val dec = DecimalFormat("#,###")
        binding.price.text = "${ dec.format(mProduct.price) }???"

    }

    private fun initAdapter() {
        mPagerAdapter = ProductDetailViewPagerAdapter(this)
        binding.productDetailViewPager.adapter = mPagerAdapter

        TabLayoutMediator(binding.productDetailTabLayout, binding.productDetailViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "????????????"
                1 -> tab.text = "????????????"
                2 -> tab.text = "??????"
                else -> tab.text = "??????"
            }
        }.attach()
    }

    override fun initAppbar() {

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