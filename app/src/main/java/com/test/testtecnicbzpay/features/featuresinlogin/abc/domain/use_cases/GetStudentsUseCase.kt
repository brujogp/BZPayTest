package com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.use_cases

import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.extensions.convertStudent
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository.StudentsRepository
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.GetStudentsState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) {
    operator fun invoke() = flow {
        emit(ResponseState.Loading())
        val response = studentsRepository.getStudentsList()

        if (response.meta.status == "SUCCESS") {
            emit(ResponseState.Success(GetStudentsState(studentsList = response.studentsList?.convertStudents())))
        } else if (response.meta.status == "ERROR") {
            emit(ResponseState.Error(response.meta.error))
        }
    }

    private fun List<Student>.convertStudents(): List<StudentEntityDto> {
        val newList = mutableListOf<StudentEntityDto>()
        forEach { student ->
            newList.add(student.convertStudent())
        }
        return newList
    }
}

