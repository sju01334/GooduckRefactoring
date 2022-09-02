package com.gooduckrefactoring.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.repository.remote.CartRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.*
import kotlinx.coroutines.launch

class CartViewModel() : ViewModel() {
    private val list = mutableListOf<Cart>()
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
                    list.clear()
                    list.addAll(it.data.data!!.carts)
                    _cartItemList.postValue(list)
                }
            }

        }
    }

    fun addToCartItem(id: Int) {
        viewModelScope.launch {
            repository!!.postRequestAddCart(id) {
                if (it is Result.Success) {
                    list.add(it.data.data!!.cart)
                    _cartItemList.postValue(list)
                }
            }

        }
    }


}




