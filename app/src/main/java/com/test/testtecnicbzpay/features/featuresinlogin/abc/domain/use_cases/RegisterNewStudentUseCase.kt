package com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.use_cases

import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository.StudentsRepository
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.RegisterNewStudentState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterNewStudentUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) {
    operator fun invoke(newStudent: StudentEntityDto): Flow<ResponseState<RegisterNewStudentState>> =
        flow {
            emit(ResponseState.Loading())

            val response = studentsRepository.registerNewStudent(newStudent)
            if (response.meta.status == "SUCCESS") {
                emit(ResponseState.Success(RegisterNewStudentState(isSuccess = true)))
            } else if (response.meta.status == "ERROR") {
                emit(ResponseState.Success(RegisterNewStudentState(error = response.meta.error)))
            }
        }
}