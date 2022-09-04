package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.repository.cart.CartDatasource
import com.gooduckrefactoring.repository.cart.CartRepository

class CartViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository = CartRepository(CartDatasource())) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}