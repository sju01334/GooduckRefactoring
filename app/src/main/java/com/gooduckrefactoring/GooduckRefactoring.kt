package com.gooduckrefactoring

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GooduckRefactoring : Application() {
    override fun onCreate() {
        super.onCreate()
//        Kakao SDK 초기화
        KakaoSdk.init(this, "4a4a43a6cf8ba4b8e5dee89bc53e5b0c")
    }
}