package com.test.testtecnicbzpay.features.login.data.dtos

import androidx.activity.result.IntentSenderRequest
import com.test.testtecnicbzpay.commons.data.dtos.Meta

data class LoginWithGoogleDTO(
    var origin: Int = GOOGLE_ORIGIN,
    var intentSender: IntentSenderRequest? = null,
    var meta: Meta = Meta()
) {
    companion object {
        const val GOOGLE_ORIGIN = 11
    }
}