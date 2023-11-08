package com.test.testtecnicbzpay.features.login.data.repository

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.features.login.data.dtos.LoginDTO
import com.test.testtecnicbzpay.features.login.data.dtos.Meta
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl : LoginRepository {

    override suspend fun loginWithGoogle(context: Activity): LoginDTO {
        val oneTapClient = Identity.getSignInClient(context)
        val signInRequest = configRequest(context)
        val resultTask = oneTapClient.beginSignIn(signInRequest)

        return suspendCoroutine { continuation ->
            if (context is Activity) {
                resultTask
                    .addOnSuccessListener(context) { result ->
                        try {
                            continuation.resume(
                                LoginDTO(
                                    intentSender = IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                                        .build(),
                                    meta = Meta("SUCCESS", "")
                                )
                            )

                        } catch (e: IntentSender.SendIntentException) {
                            continuation.resume(
                                LoginDTO(
                                    intentSender = null,
                                    meta = Meta("ERROR", "")
                                )
                            )
                        }
                    }.addOnFailureListener {
                        continuation.resume(
                            LoginDTO(
                                intentSender = null,
                                meta = Meta("ERROR", "")
                            )
                        )
                    }
            }
        }

        return LoginDTO(
            intentSender = null,
            meta = Meta("ERROR", "")
        )
    }

    private fun configRequest(context: Context) = BeginSignInRequest.builder()
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

    override suspend fun loginWithEmail(): LoginDTO {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmail(): LoginDTO {
        TODO("Not yet implemented")
    }

    fun BeginSignInRequest.waitForResponse() {

    }
}

