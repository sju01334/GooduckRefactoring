package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.*
import com.gooduckrefactoring.data.product.ProductRepository
import com.gooduckrefactoring.data.review.ReviewRepository
import com.gooduckrefactoring.data.Result
import com.nepplus.gooduck.models.Banner
import com.nepplus.gooduck.models.Product
import com.nepplus.gooduck.models.Review
import kotlinx.coroutines.launch

class ReviewViewModel(private val reviewRepository: ReviewRepository) : ViewModel() {

    private val _reviewItemList: MutableLiveData<List<Review>> = MutableLiveData()
    val reviewItemList: LiveData<List<Review>> get() = _reviewItemList

    init {
        getAllReviews()
    }

    fun getAllReviews() {
        viewModelScope.launch {
            reviewRepository.getRequestAllReview {
                if (it is Result.Success) {
                    val reviews = it.data.data!!.reviews
                    _reviewItemList.value = reviews.distinctBy { it.product.name }.take(11)
                }
            }

        }
    }


}




