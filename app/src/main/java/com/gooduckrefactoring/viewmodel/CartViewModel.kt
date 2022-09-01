package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.repository.remote.CartRepository
import com.gooduckrefactoring.repository.remote.CategoryRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.Cart
import com.nepplus.gooduck.models.Category
import com.nepplus.gooduck.models.Product
import kotlinx.coroutines.launch

class CartViewModel() : ViewModel() {

    private val _cartItemList: MutableLiveData<List<Cart>> = MutableLiveData()
    val cartItemList: LiveData<List<Cart>> get() = _cartItemList


    private val repository by lazy {
        CartRepository.getInstance()
    }

    init {
        getAllCartItems()
    }


    fun getAllCartItems() {
        viewModelScope.launch {
            repository!!.getRequestMyCartList {
                if (it is Result.Success) {
                    _cartItemList.value = it.data.data!!.carts
                }
            }

        }
    }

    fun addToCartItem(id: Int) {
        viewModelScope.launch {
            repository!!.postRequestAddCart(id) {
                if (it is Result.Success) {
                }
            }

        }
    }


}




