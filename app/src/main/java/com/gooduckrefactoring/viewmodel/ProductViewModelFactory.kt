package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.repository.product.ProductDatasouce
import com.gooduckrefactoring.repository.product.ProductRepository

class ProductViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(ProductRepository(ProductDatasouce())) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}