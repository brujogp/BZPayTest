package com.test.testtecnicbzpay.features.login.domain

import androidx.fragment.app.FragmentActivity
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.login.data.repository.LoginRepository
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithEmailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithEmailUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        activity: FragmentActivity,
    ): Flow<ResponseState<LoggingWithEmailState>> = flow {
        emit(ResponseState.Loading())

        val response = loginRepository.loginWithEmail(email, password, activity)
        if (response.meta.status == "SUCCESS") {
            emit(ResponseState.Success(LoggingWithEmailState(user = response.user)))
        } else if (response.meta.status == "ERROR") {
            emit(ResponseState.Error(response.meta.error))
        }
    }
}
