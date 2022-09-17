package com.gooduckrefactoring.repository.review

import com.gooduckrefactoring.repository.Result
import com.gooduckrefactoring.dto.BasicResponse

class ReviewRepository(private val reviewDatasource : ReviewDatasource) {

    companion object {
        private var instance: ReviewRepository? = null

        fun getInstance(): ReviewRepository? { // singleton pattern
            if (instance == null) {
                val reviewDatasource = ReviewDatasource()
                instance = ReviewRepository(reviewDatasource)
            }
            return instance
        }
    }

    fun getRequestAllReview(result: (Result<BasicResponse>) -> Unit) {
        reviewDatasource.getRequestAllReview(result)
    }







}


