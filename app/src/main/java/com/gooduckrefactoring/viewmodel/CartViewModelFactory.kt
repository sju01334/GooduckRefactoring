package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.data.cart.CartDatasource
import com.gooduckrefactoring.data.cart.CartRepository

class CartViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository = CartRepository(CartDatasource())) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}