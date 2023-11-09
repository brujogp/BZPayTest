package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states

import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto

data class RegisterNewStudentState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var error: String? = null
)