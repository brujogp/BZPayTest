package com.test.testtecnicbzpay.features.inloginfeatures.abc.data.extensions

import com.test.testtecnicbzpay.features.inloginfeatures.abc.data.database.entities.Student
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto

fun Student.convertStudent(): StudentEntityDto = StudentEntityDto(
    id = uid,
    studentName = name,
    studentAge = age,
    subject = subject
)
