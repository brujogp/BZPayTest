package com.test.testtecnicbzpay.features.login.data.repository

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.test.testtecnicbzpay.features.login.data.dtos.LoginWithEmailDTO
import com.test.testtecnicbzpay.features.login.data.dtos.LoginWithGoogleDTO

interface LoginRepository {
    suspend fun loginWithGoogle(context: Activity): LoginWithGoogleDTO
    suspend fun loginWithEmail(
        email: String,
        password: String,
        activity: FragmentActivity
    ): LoginWithEmailDTO

    suspend fun signInWithEmail(
        email: String,
        password: String,
        activity: Activity
    ): LoginWithEmailDTO
}