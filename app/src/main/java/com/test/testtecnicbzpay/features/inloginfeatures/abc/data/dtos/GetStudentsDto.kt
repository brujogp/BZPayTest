package com.test.testtecnicbzpay.features.inloginfeatures.abc.data.dtos

import com.test.testtecnicbzpay.commons.data.dtos.Meta
import com.test.testtecnicbzpay.features.inloginfeatures.abc.data.database.entities.Student

data class GetStudentsDto(
    var studentsList: List<Student>? = null,
    var meta: Meta = Meta()
)