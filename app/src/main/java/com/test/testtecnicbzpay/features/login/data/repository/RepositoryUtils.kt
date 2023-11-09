package com.test.testtecnicbzpay.features.login.data.repository

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.test.testtecnicbzpay.R


fun configRequest(context: Context) = BeginSignInRequest.builder()
    .setPasswordRequestOptions(
        BeginSignInRequest.PasswordRequestOptions.builder()
            .setSupported(true)
            .build()
    )
    .setGoogleIdTokenRequestOptions(
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(true)
            .build()
    )
    .setAutoSelectEnabled(true)
    .build()
