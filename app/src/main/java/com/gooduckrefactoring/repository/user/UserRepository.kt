package com.gooduckrefactoring.repository.user

import com.gooduckrefactoring.repository.Result
import com.gooduckrefactoring.dto.BasicResponse
import com.nepplus.gooduck.models.UserData

class UserRepository(private val userDatasource : UserDatasource) {

    suspend fun getRequestMyInfo(result: (Result<UserData>) -> Unit) {
        userDatasource.getRequestMyInfo(result)
    }

    suspend fun postRequestLogin(email: String, pw: String, result: (Result<BasicResponse>) -> Unit) {
        userDatasource.postRequestLogin(email, pw, result)
    }

    suspend fun postRequestSocialLogin(provider: String, uid: String, nick: String, result: (Result<BasicResponse>) -> Unit) {
        userDatasource.postRequestSocialLogin(provider, uid, nick, result)

    }





}

