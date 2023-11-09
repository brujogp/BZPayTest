package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository

import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.GetStudentsDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.RegisterNewStudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto

interface StudentsRepository {
    suspend fun getStudentsList(): GetStudentsDto
    suspend fun registerNewStudent(newStudent: StudentEntityDto): RegisterNewStudentDto
}