package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.repository.cart.CartRepository
import com.gooduckrefactoring.repository.Result
import com.nepplus.gooduck.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val list = mutableListOf<Cart>()
    private val _cartItemList: MutableLiveData<List<Cart>> = MutableLiveData()
    val cartItemList: LiveData<List<Cart>> get() = _cartItemList

    private val _errorMsg: MutableLiveData<String> = MutableLiveData()
    val errorMsg: LiveData<String> get() = _errorMsg

    init {
        getAllCartItems()
    }


    fun getAllCartItems() {
        viewModelScope.launch {
            repository.getRequestMyCartList {
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
            repository.postRequestAddCart(id) {
                if (it is Result.Success) {
                    list.add(it.data.data!!.cart)
                    _cartItemList.postValue(list)
                }else if(it is Result.Error){
                    _errorMsg.value = it.exception
                }
            }

        }
    }


}




