package com.gooduckrefactoring.repository.category

import com.gooduckrefactoring.repository.Result
import com.gooduckrefactoring.dto.BasicResponse

class CategoryRepository(private val categoryDatasouce: CategoryDatasouce) {

    suspend fun getRequestAllCategory(result: (Result<BasicResponse>) -> Unit) {
      categoryDatasouce.getRequestAllCategory(result)
    }

    suspend fun getRequestProducts(id : Int , result: (Result<BasicResponse>) -> Unit) {
       categoryDatasouce.getRequestProducts(id, result)
    }






}


