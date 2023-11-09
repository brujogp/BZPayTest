package com.test.testtecnicbzpay.features.featuresinlogin.abc.domain

import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository.StudentsRepository
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository.StudentsRepositoryImpl
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.StudentsState
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithEmailState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) {
    operator fun invoke() = flow {
        emit(ResponseState.Loading())
        val response = studentsRepository.getStudentsList()

        if (response.meta.status == "SUCCESS") {
            emit(ResponseState.Success(StudentsState(studentsList = response.studentsList)))
        } else if (response.meta.status == "ERROR") {
            emit(ResponseState.Error(response.meta.error))
        }
    }
}