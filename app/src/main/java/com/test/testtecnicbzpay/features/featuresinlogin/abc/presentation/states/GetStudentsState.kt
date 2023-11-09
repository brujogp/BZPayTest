package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states

import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto

data class GetStudentsState(
    var isLoading: Boolean = false,
    var studentsList: List<StudentEntityDto>? = null,
    var error: String? = null
)