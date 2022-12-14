package com.nepplus.gooduck.api

import com.gooduckrefactoring.dto.BasicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIList {

    //user==========================================
    @GET("/user")
    fun getRequestMyInfo(): Call<BasicResponse>

    @DELETE("/user")
    fun deleteRequestUser(@Query("text") text : String): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nickname: String,
        @Field("phone") phone: String,
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserInfo(
        @Field("field") field: String,
        @Field("value") value: String,
        @Field("current_password") password: String? = null
    ): Call<BasicResponse>

    @GET("/user/check")
    fun getRequestUserCheck(
        @Query("type") type: String,
        @Query("value") value: String,
    ): Call<BasicResponse>

    @Multipart
    @PUT("/user/image")
    fun putRequestUserImage(@Part profileImg: MultipartBody.Part): Call<BasicResponse>

    @GET("/user/find/email")
    fun getReqeustFindId(
        @Query("nick_name") nickname : String,
        @Query("phone") phone : String
    ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/find/password")
    fun postReqeustFindPw(
        @Field("nick_name") nickname : String,
        @Field("email") email : String
    ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider : String,
        @Field("uid") uid : String,
        @Field("nick_name") nickname : String
    ) : Call<BasicResponse>

    @GET("/user/card")
    fun getRequestMyCard(): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/card")
    fun postRequestAddCard(
        @Field("card_num") cardNum : String,
        @Field("card_nickname") cardNick : String,
        @Field("mm_yy") mmyy : String,
        @Field("birthday") birthday : String,
        @Field("password_2digit") pw2digit : String,
    ) : Call<BasicResponse>

    @GET("/user/review")
    fun getRequestMyReview(): Call<BasicResponse>

    @GET("/user/payment")
    fun getRequestMyPayment(): Call<BasicResponse>

    @GET("/user/point")
    fun getRequestMyPoint(): Call<BasicResponse>

    //  main=====================================================
    @GET("/main/banner")
    fun getRequestBanner() : Call<BasicResponse>

    @GET("/product")
    fun getRequestAllProduct() : Call<BasicResponse>


    //  category
    @GET("/category")
    fun getRequestAllCategory() : Call<BasicResponse>

    @GET("/category/small")
    fun getRequestSmallCategory() : Call<BasicResponse>

    @GET("/category/small/{small_category_id}")
    fun getRequestProducts(@Path("small_category_id") id : Int) : Call<BasicResponse>

    @GET("/category/small/{small_category_id}/review")
    fun getRequestProductsReview(@Path("small_category_id") id : Int) : Call<BasicResponse>

    //  cart======================================================
    @FormUrlEncoded
    @POST("/cart")
    fun postRequestAddCart(@Field("product_id") productId : Int) : Call<BasicResponse>

    @GET("/cart")
    fun getRequestMyCartList() : Call<BasicResponse>

    @DELETE("/cart")
    fun deleteRequestMyCartProduct(@Query("product_id") productId : Int) : Call<BasicResponse>

    //  review=====================================================
    @GET("/review")
    fun getRequestAllReview() : Call<BasicResponse>

    @GET("/review/ranking")
    fun getRequestSortRank() : Call<BasicResponse>

    @Multipart
    @POST("/review")
    fun postRequestAddReview(
        @Part product_id: MultipartBody.Part,
        @Part title : MultipartBody.Part,
        @Part content : MultipartBody.Part,
        @Part score : MultipartBody.Part,
        @Part tags_list : MultipartBody.Part,
        @Part thumbnail_img: MultipartBody.Part?
    ) : Call<BasicResponse>

    @GET("/review/{review_id}/reply")
    fun getRequestReviewReply(@Path("review_id") id : Int) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/review/{review_id}/reply")
    fun postRequestAddReply(@Path("review_id") id : Int, @Field("content") content : String) : Call<BasicResponse>

//  purchase======================================================
    @FormUrlEncoded
    @POST("/purchase")
    fun postRequestPurchaseProduct(
        @Field("product_id") productId : Int,
        @Field("card_id") cardId : Int
    ) : Call<BasicResponse>

}