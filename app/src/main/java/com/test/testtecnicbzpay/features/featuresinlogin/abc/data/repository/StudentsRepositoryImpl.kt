package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository

import com.test.testtecnicbzpay.commons.data.dtos.Meta
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.daos.StudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.StudentsDto
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(
    private val dao: StudentDto
) : StudentsRepository {

    override suspend fun getStudentsList(): StudentsDto {
        val students = this.dao.getAll()
        return StudentsDto(
            studentsList = students,
            meta = Meta(
                status = if (students.isNotEmpty()) "SUCCESS" else "ERROR",
                error = if (students.isEmpty()) "No se tienen estudiantes" else ""
            )
        )
    }
}