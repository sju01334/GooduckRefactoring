package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.repository.HomeRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.Banner
import com.nepplus.gooduck.models.Product
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val _bannerItemList: MutableLiveData<List<Banner>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()
    private val _totalBanner: MutableLiveData<Int> = MutableLiveData()
    private val _productItemList: MutableLiveData<List<Product>> = MutableLiveData()

    val bannerItemList: LiveData<List<Banner>> get() = _bannerItemList
    val currentPosition: LiveData<Int> get() = _currentPosition
    val totalBanner: LiveData<Int> get() = _totalBanner
    val productItemList: LiveData<List<Product>> get() = _productItemList

    private val repository by lazy {
        HomeRepository.getInstance()
    }

    init {
        _currentPosition.value = 0
        _totalBanner.value = 1
        getBannerItems()
        getProductItems()
    }



    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }
    fun getCurrentPosition() = currentPosition.value

    fun getTotalBanner() = totalBanner.value

    fun getBannerItems() {
        viewModelScope.launch {
            repository!!.getRequestBanner{
                if (it is Result.Success) {
                    val bannerList =  it.data.data!!.banners
                    _bannerItemList.value = bannerList
                    _totalBanner.value = bannerList.size
                }
            }

        }
    }

    fun getProductItems() {
        viewModelScope.launch {
            repository!!.getRequestAllProduct{
                if (it is Result.Success) {
                    val productList = it.data.data!!.products.shuffled()
                    _productItemList.value = productList.take(5)
                }
            }

        }
    }




}




