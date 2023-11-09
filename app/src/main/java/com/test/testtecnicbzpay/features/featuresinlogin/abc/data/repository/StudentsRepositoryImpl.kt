package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository

import com.test.testtecnicbzpay.commons.data.dtos.Meta
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.daos.StudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.GetStudentsDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.RegisterNewStudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.extensions.convertToEntity
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(
    private val dao: StudentDto
) : StudentsRepository {

    override suspend fun getStudentsList(): GetStudentsDto {
        val students = this.dao.getAll()
        return GetStudentsDto(
            studentsList = students,
            meta = Meta(
                status = if (students.isNotEmpty()) "SUCCESS" else "ERROR",
                error = if (students.isEmpty()) "No se tienen estudiantes" else ""
            )
        )
    }

    override suspend fun registerNewStudent(newStudent: StudentEntityDto): RegisterNewStudentDto {
        return try {
            this.dao.insert(newStudent.convertToEntity())

            RegisterNewStudentDto(Meta(status = "SUCCESS"))
        } catch (e: Exception) {
            RegisterNewStudentDto(
                Meta(
                    status = "ERROR",
                    error = "Error al crear estudiante"
                )
            )
        }
    }
}

