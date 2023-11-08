package com.test.testtecnicbzpay.features.login.presentation.contracts

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.test.testtecnicbzpay.main.MainActivity

/*
class GoogleSignInContract : ActivityResultContract<Intent, String?>() {
    override fun createIntent(context: Context, input: Intent): Intent {
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        /*
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(input)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
         */
    }
}
*/