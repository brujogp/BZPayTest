package com.test.testtecnicbzpay.features.login.presentation.states

import androidx.activity.result.IntentSenderRequest

data class LoggingWithGoogleState(
    var isLoading: Boolean = false,
    var state: IntentSenderRequest? = null,
    var error: String? = null
)
