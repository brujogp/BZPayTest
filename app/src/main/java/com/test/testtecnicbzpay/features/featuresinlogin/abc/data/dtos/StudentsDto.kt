package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.dtos

import com.test.testtecnicbzpay.commons.data.dtos.Meta
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student

data class StudentsDto(
    var studentsList: List<Student>? = null,
    var meta: Meta = Meta()
)