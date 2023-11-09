package com.test.testtecnicbzpay.features.login.data.repository

import android.app.Activity
import android.content.IntentSender
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.data.dtos.Meta
import com.test.testtecnicbzpay.features.login.data.dtos.LoginWithEmailDTO
import com.test.testtecnicbzpay.features.login.data.dtos.LoginWithGoogleDTO
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl : LoginRepository {
    override suspend fun loginWithGoogle(context: Activity): LoginWithGoogleDTO {
        val oneTapClient = Identity.getSignInClient(context)
        val signInRequest = configRequest(context)
        val resultTask = oneTapClient.beginSignIn(signInRequest)

        return suspendCoroutine { continuation ->
            resultTask
                .addOnSuccessListener(context) { result ->
                    try {
                        continuation.resume(
                            LoginWithGoogleDTO(
                                intentSender = IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                                    .build(),
                                meta = Meta("SUCCESS", "")
                            )
                        )

                    } catch (e: IntentSender.SendIntentException) {
                        continuation.resume(
                            LoginWithGoogleDTO(
                                intentSender = null,
                                meta = Meta("ERROR", "")
                            )
                        )
                    }
                }.addOnFailureListener {
                    continuation.resume(
                        LoginWithGoogleDTO(
                            intentSender = null,
                            meta = Meta("ERROR", "")
                        )
                    )
                }
        }
    }

    override suspend fun loginWithEmail(
        email: String,
        password: String,
        activity: FragmentActivity
    ): LoginWithEmailDTO {
        return suspendCoroutine { continuation ->
            val auth = Firebase.auth
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    continuation.resume(
                        LoginWithEmailDTO(
                            user = auth.currentUser.toString(),
                            meta = Meta(status = "SUCCESS")
                        )
                    )
                } else {
                    continuation.resume(
                        LoginWithEmailDTO(
                            meta = Meta(status = "ERROR")
                        )
                    )
                }
            }
        }
    }


    override suspend fun signInWithEmail(
        email: String,
        password: String,
        activity: Activity
    ): LoginWithEmailDTO {
        return suspendCoroutine { continuation ->
            val auth = Firebase.auth
            auth.createUserWithEmailAndPassword(
                email, password
            ).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    continuation.resume(
                        LoginWithEmailDTO(
                            user = auth.currentUser.toString(),
                            meta = Meta(status = "SUCCESS")
                        )
                    )
                } else {
                    continuation.resume(
                        LoginWithEmailDTO(
                            meta = Meta(status = "ERROR")
                        )
                    )
                }
            }
        }
    }
}

