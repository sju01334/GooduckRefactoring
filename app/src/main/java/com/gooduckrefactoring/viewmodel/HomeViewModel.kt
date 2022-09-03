package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.*
import com.gooduckrefactoring.data.home.HomeRepository
import com.gooduckrefactoring.data.ReviewRepository
import com.gooduckrefactoring.data.Result
import com.nepplus.gooduck.models.Banner
import com.nepplus.gooduck.models.Product
import com.nepplus.gooduck.models.Review
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    //banner
    private val _bannerItemList: MutableLiveData<List<Banner>> = MutableLiveData()
    val bannerItemList: LiveData<List<Banner>> get() = _bannerItemList

    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()
    val currentPosition: LiveData<Int> get() = _currentPosition

    private val _totalBanner: MutableLiveData<Int> = MutableLiveData()
    val totalBanner: LiveData<Int> get() = _totalBanner


    // product
    private val _productItemList: MutableLiveData<List<Product>> = MutableLiveData()
    val productItemList: LiveData<List<Product>> get() = _productItemList

    private val _productItemListAll: MutableLiveData<List<Product>> = MutableLiveData()
    val productItemListAll: LiveData<List<Product>> get() = _productItemListAll

    private val _productItem: MutableLiveData<List<Product>> = MutableLiveData()
    val productItem: LiveData<List<Product>> get() = _productItem

    //search
    private val _recommendItem: MutableLiveData<List<String>> = MutableLiveData()
    val recommendItem: LiveData<List<String>> get() = _recommendItem

    private val _reviewItemList: MutableLiveData<List<Review>> = MutableLiveData()
    val reviewItemList: LiveData<List<Review>> get() = _reviewItemList

    private val homeRepository by lazy {
        HomeRepository.getInstance()
    }

    private val reviewRepository by lazy {
        ReviewRepository.getInstance()
    }

    init {

        _currentPosition.value = 0
        _totalBanner.value = 1

        _recommendItem.value = listOf("스팸", "호빵", "왕란", "콘푸로스트", "딸기", "덧신" ,"이것", "저것넣어볼까", "줄바뀌는거볼래")
        getBannerItems()
        getProductItems()
        getAllReviews()
    }


    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getCurrentPosition() = currentPosition.value

    fun getTotalBanner() = totalBanner.value

    fun getBannerItems() {
        viewModelScope.launch {
            homeRepository!!.getRequestBanner {
                if (it is Result.Success) {
                    val bannerList = it.data.data!!.banners
                    _bannerItemList.value = bannerList
                    _totalBanner.value = bannerList.size
                }
            }

        }
    }

    fun getProductItems() {
        viewModelScope.launch {
            homeRepository!!.getRequestAllProduct {
                if (it is Result.Success) {
                    _productItemListAll.value  = it.data.data!!.products
                    val productList = it.data.data!!.products.shuffled()
                    _productItemList.value = productList.take(5)
                }
            }

        }
    }

    fun getProduct(id: Int) {
        viewModelScope.launch {
            homeRepository!!.getRequestProducts(id) {
                if (it is Result.Success) {
                    _productItem.value = it.data.data!!.products
                }
            }

        }
    }

    fun getAllReviews() {
        viewModelScope.launch {
            reviewRepository!!.getRequestAllReview {
                if (it is Result.Success) {
                    val reviews = it.data.data!!.reviews
                    _reviewItemList.value = reviews.distinctBy { it.product.name }.take(11)
                }
            }

        }
    }


}




