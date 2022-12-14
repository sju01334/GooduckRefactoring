package com.gooduckrefactoring.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.repository.review.ReviewDatasource
import com.gooduckrefactoring.repository.review.ReviewRepository

class ReviewViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(ReviewRepository(ReviewDatasource())) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}