package com.test.testtecnicbzpay.features.login.data.repository

import android.app.Activity
import android.content.Context
import com.test.testtecnicbzpay.features.login.data.dtos.LoginDTO

interface LoginRepository {
    suspend fun loginWithGoogle(context: Activity): LoginDTO
    suspend fun loginWithEmail(): LoginDTO
    suspend fun signInWithEmail(): LoginDTO
}