package com.test.testtecnicbzpay.features.login.presentation.states


data class LoggingWithEmailState(
    var isLoading: Boolean = false,
    var user: String? = null,
    var error: String? = null
)