package com.gooduckrefactoring.view

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gooduckrefactoring.R
import com.gooduckrefactoring.adapter.ProductFullRecyclerviewAdapter
import com.gooduckrefactoring.databinding.ActivityProductsBinding
import com.gooduckrefactoring.util.MyItemDecoration
import com.gooduckrefactoring.viewmodel.CartViewModel
import com.gooduckrefactoring.viewmodel.CartViewModelFactory
import com.gooduckrefactoring.viewmodel.CategoryViewModel
import com.gooduckrefactoring.viewmodel.CategoryViewModelFactory
import com.nepplus.gooduck.models.SmallCategory

class ProductsActivity() : BaseActivity<ActivityProductsBinding>() {
    override val layoutId: Int = R.layout.activity_products
    lateinit var category: SmallCategory
    lateinit var productAdapter : ProductFullRecyclerviewAdapter

    private val categoryViewModel by lazy {
        ViewModelProvider(this, CategoryViewModelFactory())[CategoryViewModel::class.java]
    }

    private val cartViewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory())[CartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = intent.getSerializableExtra("selected") as SmallCategory

        initAppbar()
        setValues()
        setupEvents()
        initRecyclerviewAdapter()
    }

    private fun initRecyclerviewAdapter() {
        binding.selectedCategoryProductRecyclerView.apply {
            productAdapter = ProductFullRecyclerviewAdapter(){
                cartViewModel.addToCartItem(it.id)
                Toast.makeText(this@ProductsActivity, "${it.name}을 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
            }
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@ProductsActivity, 2)
            addItemDecoration(MyItemDecoration(2, 20, false))
        }
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        categoryViewModel.getSelectedCategoryItems(category.id)

        categoryViewModel.selectedCategoryItemList.observe(this){
            productAdapter.submitList(it)
            binding.productCntTxt.text = "총 ${it.size}개"
        }
    }

    override fun initAppbar() {
        logo.isVisible = false
        titleTxt.text = category.name
    }

}