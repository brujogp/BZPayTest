package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.extensions

import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto

fun Student.convertStudent(): StudentEntityDto = StudentEntityDto(
    id = uid,
    studentName = name,
    studentAge = age,
    subject = subject
)
