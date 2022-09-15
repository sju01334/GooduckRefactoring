package com.gooduckrefactoring.view

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProduct = intent.getSerializableExtra("product") as Product

        initAdapter()
        setValues()
        setupEvents()
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

        cartViewModel.errorMsg.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        cartViewModel.successFlag.observe(this){
            cartViewModel.cartItemList.value?.let {
                Toast.makeText(this,
                    "${cartViewModel.cartItemList.value!!.last().product.name} 을 장바구니에 담았습니다",
                    Toast.LENGTH_SHORT).show()
            }
        }


        cartViewModel.cartItemList.observe(this){
            binding.cartCnt.text = it.size.toString()
        }


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