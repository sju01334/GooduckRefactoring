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
import com.nepplus.gooduck.models.SignInData
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import com.nepplus.gooduck.utils.GlobalData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel(
    application: Application,
    private val repository: UserRepository,
) : AndroidViewModel(application) {
//    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()
//    val navigateToDetails : LiveData<Event<Boolean>>
//        get() = _navigateToDetails

    var signInData = SignInData()

    private var _isEmailError = MutableLiveData<Boolean>()
    val isEmailError: LiveData<Boolean> get() = _isEmailError

    private var _isEmailDupli = MutableLiveData<Boolean>()
    val isEmailDupli: LiveData<Boolean> get() = _isEmailDupli

    private var _isPWError = MutableLiveData<Boolean>()
    val isPWError: LiveData<Boolean> get() = _isPWError

    private var _isNickError = MutableLiveData<Boolean>()
    val isNickError: LiveData<Boolean> get() = _isNickError

    private var _isNickDupli = MutableLiveData<Boolean>()
    val isNickDupli: LiveData<Boolean> get() = _isNickDupli

    private var _isPhoneError = MutableLiveData<Boolean>()
    val isPhoneError: LiveData<Boolean> get() = _isPhoneError

    private var _response = MutableLiveData<BasicResponse>()
    val response: LiveData<BasicResponse> get() = _response

    private var _signInResponse = MutableLiveData<UserData>()
    val signInResponse: LiveData<UserData> get() = _signInResponse

    private var _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> get() = _errorMessage


    init {
        _isEmailError.value = false
        _isEmailDupli.value = true
        _isPWError.value = false
        _isPhoneError.value = true
        _errorMessage.value = null
        _isNickDupli.value = true
    }

    fun isEmailValid(editable: Editable) {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(editable).matches()
    }

    fun isEmailDuplicateCheck(editable: Editable) {
        viewModelScope.launch {
            repository.getRequestUserCheck("EMAIL", editable.toString()) {
                if (it is Result.Success) {
                    _isEmailDupli.value = true
                } else if (it is Result.Error) {
                    _isEmailDupli.value = false
                }
            }
        }

    }

    fun isNickDuplicateCheck(editable: Editable) {
        viewModelScope.launch {
            repository.getRequestUserCheck("NICK_NAME", editable.toString()) {
                if (it is Result.Success) {
                    _isNickDupli.value = true
                } else if (it is Result.Error) {
                    _isNickDupli.value = false
                }
            }
        }
    }

    fun isPasswordValid(editable: Editable) {
        _isPWError.value = editable.length < 5
    }

    fun isPhoneValid(editable: Editable) {
        _isPhoneError.value = editable.length in 0..10
    }

    fun normalLogin(email: String, pw: String) {
        if (_isEmailError.value == false && _isPWError.value == false) {
            viewModelScope.launch {
                repository.postRequestLogin(email = email, pw = pw) {
                    if (it is Result.Success) {
                        _response.value = it.data
                        ContextUtil.setLoginToken(getApplication(), it.data.data!!.token)
//                        Log.d("???????????????", it.data.data.toString())
                    } else if (it is Result.Error) {
                        _errorMessage.value = it.exception
                    }
                }
            }
        } else {
            _errorMessage.value = "????????? ????????? ??????????????????"
        }
    }

    fun socialLoginFromServer(provider: String, uid: String, nick: String) {
        viewModelScope.launch {
            repository.postRequestSocialLogin(provider, uid, nick) {
                if (it is Result.Success) {
                    _response.value = it.data
//                        Log.d("???????????????", it.data.data.toString())
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
        // ?????????????????? ????????? ??? ??? ?????? ????????????????????? ???????????? ?????? ?????????
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao_TAG", "????????????????????? ????????? ??????", error)
            } else if (token != null) {
                Log.i("kakao_TAG", "????????????????????? ????????? ?????? ${token.accessToken}")
                getKakaoUser()
            }
        }

        // ??????????????? ???????????? ????????? ?????????????????? ?????????, ????????? ????????????????????? ?????????
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(getApplication())) {
            UserApiClient.instance.loginWithKakaoTalk(getApplication()) { token, error ->
                if (error != null) {
                    Log.e("kakao_TAG", "?????????????????? ????????? ??????", error)

                    // ???????????? ???????????? ?????? ??? ???????????? ?????? ?????? ???????????? ???????????? ????????? ??????,
                    // ???????????? ????????? ????????? ?????? ????????????????????? ????????? ?????? ?????? ????????? ????????? ?????? (???: ?????? ??????)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // ??????????????? ????????? ?????????????????? ?????? ??????, ????????????????????? ????????? ??????
                    UserApiClient.instance.loginWithKakaoAccount(getApplication(), callback = callback)
                } else if (token != null) {
                    Log.i("kakao_TAG", "?????????????????? ????????? ?????? ${token.accessToken}")
                    getKakaoUser()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(getApplication(), callback = callback)
        }
    }

    fun getKakaoUser() {
        // ????????? ?????? ?????? (??????)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao_TAG", "????????? ?????? ?????? ??????", error)
            } else if (user != null) {
                Log.i("kakao_TAG", "????????? ?????? ?????? ??????" +
                        "\n????????????: ${user.id}" +
                        "\n?????????: ${user.kakaoAccount?.profile?.nickname}")
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

        /** OAuthLoginCallback??? authenticate() ????????? ?????? ??? ??????????????? ??????????????? NidOAuthLoginButton ????????? ????????????
         * ????????? ???????????? ?????? ????????? ??? ????????????. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // ????????? ????????? ????????? ???????????? ??? ????????? ?????? ??????
                naverToken = NaverIdLoginSDK.getAccessToken()

                //????????? ?????? ?????? ????????????
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

                Log.d("?????? ????????????", family + nick)

                (nick ?: family)?.let { socialLoginFromServer("google", tokenId.toString(), it) }

            } ?: throw Exception()
        } catch (e: Exception) {
            Log.d("google_login_failed", e.message.toString())
            e.printStackTrace()
        }
    }

    fun signInToServer() {
        Log.d("?????????", signInData.email.toString())
        Log.d("??????", signInData.password.toString())
        Log.d("??????", signInData.nickname.toString())
        Log.d("??????", signInData.phone.toString())
        viewModelScope.launch {
            repository.putRequestSignUp(
                signInData.email!!.toString(),
                signInData.password!!.toString(),
                signInData.nickname!!.toString(),
                signInData.phone!!.toString()) {
                if (it is Result.Success) {
                    _signInResponse.value = it.data
                } else if (it is Result.Error) {
                    _errorMessage.value = it.exception
                }
            }
        }
    }


}





