package com.gooduckrefactoring.repository.product

import com.gooduckrefactoring.repository.Result
import com.gooduckrefactoring.dto.BasicResponse

class ProductRepository(private val productDatasouce: ProductDatasouce) {

    suspend fun getRequestBanner(result: (Result<BasicResponse>) -> Unit) {
        productDatasouce.getRequestBanner(result)

    }
    suspend fun getRequestAllProduct(result: (Result<BasicResponse>) -> Unit) {
      productDatasouce.getRequestAllProduct(result)

    }

    suspend fun getRequestProducts(id : Int , result: (Result<BasicResponse>) -> Unit) {
        productDatasouce.getRequestProducts(id, result)

    }






}


