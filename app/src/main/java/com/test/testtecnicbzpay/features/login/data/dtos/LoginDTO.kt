package com.test.testtecnicbzpay.features.login.data.dtos

import androidx.activity.result.IntentSenderRequest

data class LoginDTO(
    var origin: Int = GOOGLE_ORIGIN,
    var intentSender: IntentSenderRequest? = null,
    var meta: Meta = Meta()
) {
    companion object {
        const val GOOGLE_ORIGIN = 11
    }
}

data class Meta(
    var status: String = "",
    var error: String = ""
)