package com.example.xlwapp.viewmodel.firebaseUI

import android.app.Activity
import android.text.format.DateUtils
import android.view.View
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI

const val SIGN_IN_RESULT_CODE = 1001

class FirebaseLoginViewModel(
        private val activity: Activity,
        private val viewLifecycleOwner: LifecycleOwner
) : ViewModel() {
    private val _isLogin = MutableLiveData<String>()
    val isLogin: LiveData<String>
        get() = _isLogin

    private val _loginText = MutableLiveData<String>()
    val loginText: LiveData<String>
        get() = _loginText

    private val _loginState = MutableLiveData<Boolean>()
    val loginState = Transformations.map(_loginState){
        if (it) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private val _goSetting = MutableLiveData<Boolean>()
    val goSetting: LiveData<Boolean>
        get() = _goSetting

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    private var firebaseAuth: FirebaseUserLiveData = FirebaseUserLiveData()
    private var authenticationState: LiveData<AuthenticationState>

    init {
        authenticationState = Transformations.map(firebaseAuth){
            if (it != null) {
                AuthenticationState.AUTHENTICATED
            } else {
                AuthenticationState.UNAUTHENTICATED
            }
        }
        _loginState.value = false
        getLoginState()
    }

    private fun getLoginState() {
        authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> {
                    _isLogin.value =  "logout"
                    if ( _loginText.value.isNullOrEmpty()
                            || _loginText.value.equals("hasn't logged in")) {
                        _loginText.value = "hello " + firebaseAuth.value!!.displayName
                    }
                    _loginState.value = true
                }
                else -> {
                    _isLogin.value =  "login"
                    _loginText.value = "hasn't logged in"
                    _loginState.value = false
                }
            }
        })
    }

    fun onLoginClicked() {
        if(_loginState.value == null || !_loginState.value!!){
            startLogin()
        }else{
            startLogout()
        }
    }

    private fun startLogin(){
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()

                // This is where you can provide more ways for users to register and
                // sign in.
        )

        // Create and launch the sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE.
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                SIGN_IN_RESULT_CODE
        )
    }

    private fun startLogout() {
        AuthUI.getInstance().signOut(activity.applicationContext)
    }

    fun onSettingClicked(){
        _goSetting.value = true
    }

    fun onGoSetting() {
        _goSetting.value = false
    }

    fun setText(text: String){
        if(text.isNotEmpty()){
            _loginText.value = text
        }
    }

}