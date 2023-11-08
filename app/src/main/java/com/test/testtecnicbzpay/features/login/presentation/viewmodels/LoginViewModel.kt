package com.test.testtecnicbzpay.features.login.presentation.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.login.domain.LoginWithGoogleUseCase
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase
) : ViewModel() {
    private val _loggingState = MutableLiveData<LoggingState>()
    val loggingState: LiveData<LoggingState> = _loggingState

    fun signInWithEmail(activity: Activity) {
    }

    fun logInWithEmail() {
    }

    fun signInWithGoogle(activity: Activity) {
        viewModelScope.launch {
            loginWithGoogleUseCase.invoke(activity)
                .collect { responseStatus: ResponseState<LoggingState> ->
                    when (responseStatus) {
                        is ResponseState.Loading -> {
                            _loggingState.value = LoggingState(isLoading = true)
                        }

                        is ResponseState.Success -> {
                            _loggingState.value = LoggingState(state = responseStatus.data?.state)
                        }

                        is ResponseState.Error -> {
                            _loggingState.value = LoggingState(error = responseStatus.message)
                        }
                    }
                }
        }
    }
}