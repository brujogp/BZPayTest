package com.test.testtecnicbzpay.features.login.presentation.viewmodels

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.login.domain.LoginWithEmailUseCase
import com.test.testtecnicbzpay.features.login.domain.LoginWithGoogleUseCase
import com.test.testtecnicbzpay.features.login.domain.SignInWithEmailUseCase
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithEmailState
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithGoogleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val loginWithEmailUseCase: LoginWithEmailUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {
    private val _loggingWithGoogleState = MutableLiveData<LoggingWithGoogleState>()
    val loggingWithGoogleState: LiveData<LoggingWithGoogleState> = _loggingWithGoogleState

    private val _loggingWithEmailState = MutableLiveData<LoggingWithEmailState>()
    val loggingWithEmailState: LiveData<LoggingWithEmailState> = _loggingWithEmailState

    private val _signInWithEmailState = MutableLiveData<LoggingWithEmailState>()
    val signInWithEmailState: LiveData<LoggingWithEmailState> = _signInWithEmailState

    fun signInWithEmail(email: String, password: String, activity: Activity) {
        viewModelScope.launch {
            signInWithEmailUseCase.invoke(email, password, activity)
                .collect { responseStatus: ResponseState<LoggingWithEmailState> ->
                    when (responseStatus) {
                        is ResponseState.Loading -> {
                            _signInWithEmailState.value = LoggingWithEmailState(isLoading = true)
                        }

                        is ResponseState.Success -> {
                            _signInWithEmailState.value =
                                LoggingWithEmailState(user = responseStatus.data?.user)
                        }

                        is ResponseState.Error -> {
                            _signInWithEmailState.value =
                                LoggingWithEmailState(error = responseStatus.message)
                        }
                    }
                }
        }
    }

    fun logInWithEmail(email: String, password: String, activity: FragmentActivity) {
        viewModelScope.launch {
            loginWithEmailUseCase.invoke(email, password, activity)
                .collect { responseStatus: ResponseState<LoggingWithEmailState> ->
                    when (responseStatus) {
                        is ResponseState.Loading -> {
                            _loggingWithEmailState.value = LoggingWithEmailState(isLoading = true)
                        }

                        is ResponseState.Success -> {
                            _loggingWithEmailState.value =
                                LoggingWithEmailState(user = responseStatus.data?.user)
                        }

                        is ResponseState.Error -> {
                            _loggingWithEmailState.value =
                                LoggingWithEmailState(error = responseStatus.message)
                        }
                    }
                }
        }
    }

    fun signInWithGoogle(activity: Activity) {
        viewModelScope.launch {
            loginWithGoogleUseCase.invoke(activity)
                .collect { responseStatus: ResponseState<LoggingWithGoogleState> ->
                    when (responseStatus) {
                        is ResponseState.Loading -> {
                            _loggingWithGoogleState.value = LoggingWithGoogleState(isLoading = true)
                        }

                        is ResponseState.Success -> {
                            _loggingWithGoogleState.value =
                                LoggingWithGoogleState(state = responseStatus.data?.state)
                        }

                        is ResponseState.Error -> {
                            _loggingWithGoogleState.value =
                                LoggingWithGoogleState(error = responseStatus.message)
                        }
                    }
                }
        }
    }
}