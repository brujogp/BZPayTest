package com.test.testtecnicbzpay.features.login.domain

import android.app.Activity
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.login.data.dtos.LoginWithGoogleDTO
import com.test.testtecnicbzpay.features.login.data.repository.LoginRepository
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithGoogleState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(
        activity: Activity,
    ): Flow<ResponseState<LoggingWithGoogleState>> = flow {
        emit(ResponseState.Loading())
        val response: LoginWithGoogleDTO = loginRepository.loginWithGoogle(activity)

        if (response.meta.status == "SUCCESS") {
            emit(ResponseState.Success(LoggingWithGoogleState(state = response.intentSender)))
        } else if (response.meta.status == "ERROR") {
            emit(ResponseState.Error(response.meta.error))
        }
    }
}