package com.test.testtecnicbzpay.features.login.data.dtos

import com.test.testtecnicbzpay.commons.data.dtos.Meta

data class LoginWithEmailDTO(
    var origin: Int = GOOGLE_ORIGIN,
    var user: String? = null,
    var meta: Meta = Meta()
) {
    companion object {
        const val GOOGLE_ORIGIN = 11
        const val EMAIL_ORIGIN = 12
    }
}
