package com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.use_cases

import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.inloginfeatures.abc.data.repository.StudentsRepository
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.states.ActionWithStudentState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteStudentUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) {
    operator fun invoke(student: StudentEntityDto): Flow<ResponseState<ActionWithStudentState>> =
        flow {
            emit(ResponseState.Loading())

            val response = studentsRepository.deleteStudent(student)
            if (response.meta.status == "SUCCESS") {
                emit(ResponseState.Success(ActionWithStudentState(isSuccess = true)))
            } else if (response.meta.status == "ERROR") {
                emit(ResponseState.Success(ActionWithStudentState(error = response.meta.error)))
            }
        }
}
