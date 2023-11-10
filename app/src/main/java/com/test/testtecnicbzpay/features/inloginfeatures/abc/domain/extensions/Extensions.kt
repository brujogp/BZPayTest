package com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.extensions

import com.test.testtecnicbzpay.features.inloginfeatures.abc.data.database.entities.Student
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto

fun StudentEntityDto.convertToEntity(): Student = Student(
    name = studentName, subject = subject, age = studentAge
)

fun StudentEntityDto.convertToEntityWithId(): Student = Student(
    uid = id!!, name = studentName, subject = subject, age = studentAge
)
