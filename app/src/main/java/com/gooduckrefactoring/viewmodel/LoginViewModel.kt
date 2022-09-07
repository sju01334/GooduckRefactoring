package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.repository.user.UserRepository
import com.gooduckrefactoring.repository.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel(
    application: Application,
    private val repository : UserRepository
) : AndroidViewModel(application) {
//    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()
//    val navigateToDetails : LiveData<Event<Boolean>>
//        get() = _navigateToDetails
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private var _isEmailError = MutableLiveData<Boolean>()
    private var _isPWError = MutableLiveData<Boolean>()
    private var _response = MutableLiveData<BasicResponse>()
    private var _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> get() = _errorMessage


    val isEmailError: LiveData<Boolean> get() = _isEmailError
    val isPWError: LiveData<Boolean> get() = _isPWError
    val response: LiveData<BasicResponse> get() = _response

    init {
        _isEmailError.value = false
        _isPWError.value = false
        _errorMessage.value = null
    }

    fun isEmailValid(editable: Editable) {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(editable).matches()
    }

    fun isPasswordValid(editable: Editable) {
        _isPWError.value = editable.length < 5
    }

    fun normalLogin(email: String, pw: String) {

        if (_isEmailError.value == false && _isPWError.value == false) {
            viewModelScope.launch {
                repository.postRequestLogin(email = email, pw = pw) {
                    if (it is Result.Success) {
                        _response.value = it.data
                        ContextUtil.setLoginToken(getApplication(), it.data.data!!.token)
//                        Log.d("로그인정보", it.data.data.toString())
                    } else if (it is Result.Error) {
                        _errorMessage.value = it.exception
                    }
                }
            }
        } else {
            _errorMessage.value = "알맞는 정보를 입력해주세요"
        }


    }

    fun socialLoginFromServer(provider: String, uid: String, nick: String) {
        viewModelScope.launch {
            repository.postRequestSocialLogin(provider, uid, nick) {
                if (it is Result.Success) {
                    _response.value = it.data
//                        Log.d("로그인정보", it.data.data.toString())
                } else if (it is Result.Error) {
                    _errorMessage.value = it.exception
                }
            }
        }
//    fun userClicksOnButton(itemId: Boolean, email : String, pw : String) {
////        normalLogin(email, pw)
//        _navigateToDetails.value = Event(itemId)
//    }

    }

    fun kakaoLogin() {
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao_TAG", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("kakao_TAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
                getKakaoUser()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(getApplication())) {
            UserApiClient.instance.loginWithKakaoTalk(getApplication()) { token, error ->
                if (error != null) {
                    Log.e("kakao_TAG", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(getApplication(), callback = callback)
                } else if (token != null) {
                    Log.i("kakao_TAG", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    getKakaoUser()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(getApplication(), callback = callback)
        }
    }

    fun getKakaoUser() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao_TAG", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i("kakao_TAG", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")
                socialLoginFromServer("kakao", "${user.id}", "${user.kakaoAccount?.profile?.nickname}")
//                socialLogin("kakao", "${user.id}", "${user.kakaoAccount?.profile?.nickname}")
            }
        }
    }

    fun naverLogin() {

        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                val nickName = response.profile?.nickname
                socialLoginFromServer("naver", "${userId}", "${nickName}")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("Naver_TAG", "errorCode: ${errorCode}\n" + "errorDescription: $errorDescription")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면
         * 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("Naver_TAG", "errorCode: ${errorCode}\n" + "errorDescription: ${errorDescription}")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(getApplication(), oauthLoginCallback)
    }

    fun googleLogin(task: Task<GoogleSignInAccount>) {
        try {
            task.getResult(ApiException::class.java)?.let { account ->

                val tokenId = account.idToken
                val family = account.familyName
                val nick = account.displayName

                Log.d("뭐가 들어왔니", family+ nick)

                (nick ?: family)?.let { socialLoginFromServer("google", tokenId.toString(), it) }

            } ?: throw Exception()
        } catch (e: Exception) {
            Log.d("google_login_failed", e.message.toString())
            e.printStackTrace()
        }
    }



}





