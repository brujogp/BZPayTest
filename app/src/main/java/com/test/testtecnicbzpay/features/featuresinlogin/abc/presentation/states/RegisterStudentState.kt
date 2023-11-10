package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states

data class RegisterStudentState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var error: String? = null
)