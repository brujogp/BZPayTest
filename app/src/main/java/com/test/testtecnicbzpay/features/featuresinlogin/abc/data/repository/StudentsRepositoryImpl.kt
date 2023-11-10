package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository

import com.test.testtecnicbzpay.commons.data.dtos.Meta
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.daos.StudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.GetStudentsDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos.RegisterStudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.extensions.convertToEntity
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.extensions.convertToEntityWithId
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

    override suspend fun registerNewStudent(newStudent: StudentEntityDto): RegisterStudentDto {
        return try {
            this.dao.insert(newStudent.convertToEntity())

            RegisterStudentDto(Meta(status = "SUCCESS"))
        } catch (e: Exception) {
            RegisterStudentDto(
                Meta(
                    status = "ERROR",
                    error = "Error al crear estudiante"
                )
            )
        }
    }

    override suspend fun modifyStudent(studentToModify: StudentEntityDto): RegisterStudentDto {
        return try {
            this.dao.update(studentToModify.convertToEntityWithId())

            RegisterStudentDto(Meta(status = "SUCCESS"))
        } catch (e: Exception) {
            RegisterStudentDto(
                Meta(
                    status = "ERROR",
                    error = "Error al crear estudiante"
                )
            )
        }
    }
}

