package com.test.testtecnicbzpay.features.inloginfeatures.abc.data.repository

import com.test.testtecnicbzpay.features.inloginfeatures.abc.data.dtos.GetStudentsDto
import com.test.testtecnicbzpay.features.inloginfeatures.abc.data.dtos.RegisterStudentDto
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto

interface StudentsRepository {
    suspend fun getStudentsList(): GetStudentsDto
    suspend fun registerNewStudent(newStudent: StudentEntityDto): RegisterStudentDto
    suspend fun modifyStudent(newStudent: StudentEntityDto): RegisterStudentDto
    suspend fun deleteStudent(student: StudentEntityDto): RegisterStudentDto
}