package com.nepplus.gooduck.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignInData(
    var email : String? = null,
    var password  : String? = null,
    @SerializedName("nick_name")
    val nickname : String? = null,
    val phone : String? = null
): Serializable {
}