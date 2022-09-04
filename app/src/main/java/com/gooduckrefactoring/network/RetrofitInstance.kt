package com.gooduckrefactoring.network

import com.nepplus.gooduck.api.APIList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val baseUrl = "https://api.gudoc.in"
    var token : String = ""

    val interceptor = Interceptor {
        with(it) {
            val newRequest = request().newBuilder()
                .addHeader("X-Http-Token", token)
                .build()
            proceed(newRequest)
        }
    }

    val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val getRetrofit by lazy{
        Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(myClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }

    val apiList: APIList by lazy {
        getRetrofit.create(APIList::class.java)
    }


}
