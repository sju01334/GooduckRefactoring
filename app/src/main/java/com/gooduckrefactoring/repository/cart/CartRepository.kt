package com.gooduckrefactoring.repository.cart

import com.gooduckrefactoring.repository.Result
import com.gooduckrefactoring.dto.BasicResponse

class CartRepository(private val cartDatasource: CartDatasource) {

    suspend fun getRequestMyCartList(result: (Result<BasicResponse>) -> Unit) {
        cartDatasource.getRequestMyCartList(result)
    }

    suspend fun postRequestAddCart(id : Int , result: (Result<BasicResponse>) -> Unit) {
        cartDatasource.postRequestAddCart(id, result)
    }






}


