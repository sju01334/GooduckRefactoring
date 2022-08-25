package com.gooduckrefactoring

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GooduckRefactoring : Application() {
    override fun onCreate() {
        super.onCreate()
//        Kakao SDK 초기화
        KakaoSdk.init(this, "18171fed1b973091a1c0759889030a4d")
    }
}