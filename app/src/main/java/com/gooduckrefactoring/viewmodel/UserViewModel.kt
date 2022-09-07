package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.gooduckrefactoring.network.RetrofitInstance
import com.gooduckrefactoring.repository.user.UserRepository
import com.gooduckrefactoring.repository.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import com.nepplus.gooduck.utils.GlobalData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application,
    private val repository : UserRepository
) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserData>()
    val user: LiveData<UserData> get() = _user

    private var _isLogout = MutableLiveData<Boolean>()
    val isLogout: LiveData<Boolean> get() = _isLogout


    init {
        _isLogout.value = false
        RetrofitInstance.token = ContextUtil.getLoginToken(application)
        viewModelScope.launch {
            repository.getRequestMyInfo {
                if (it is Result.Success) {
                    _user.value = it.data
                } else {
                }
            }
        }
    }

    fun logout(mGoogleSignInClient: GoogleSignInClient) {
        when(GlobalData.loginUser!!.provider){
            "naver" ->{
                NaverIdLoginSDK.logout()
                startNaverDeleteToken()
            }
            "google" -> {
                mGoogleSignInClient.signOut()
            }
        }

        _isLogout.value = true

    }

    fun startNaverDeleteToken(){
        NidOAuthLogin().callDeleteTokenApi(getApplication(), object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
            }
            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }


}




