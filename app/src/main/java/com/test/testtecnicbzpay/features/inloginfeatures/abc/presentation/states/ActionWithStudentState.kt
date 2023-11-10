package com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.states

data class ActionWithStudentState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var error: String? = null
)