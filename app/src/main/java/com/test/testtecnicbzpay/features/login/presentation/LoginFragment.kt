package com.test.testtecnicbzpay.features.login.presentation

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.ui.CustomProgressDialog
import com.test.testtecnicbzpay.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var oneTapClient: SignInClient
    private lateinit var progressDialogFragment: CustomProgressDialog
    private var binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth

    private var loggingWithGoogleContract =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            val googleCredential = oneTapClient.getSignInCredentialFromIntent(it.data)
            val idToken = googleCredential.googleIdToken
            when {
                idToken != null -> {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "signInWithCredential:success")
                                findNavController().navigate(R.id.action_loginFragment_to_hostFeaturesFragment)
                                Toast.makeText(
                                    requireContext(),
                                    auth.currentUser.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Log.w("TAG", "signInWithCredential:failure", task.exception)
                            }
                        }
                }
                else -> {
                    Log.d("TAG", "No ID token!")
                }
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = setBinding(inflater, container)
        initGoogleClient()

        this.binding?.let {
            return it.root
        } ?: run {
            return null
        }
    }

    private fun initGoogleClient() {
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflate, container, false)

    private fun onLoadingDialog(title: String, message: String) {
        this.progressDialogFragment = CustomProgressDialog.newInstance(title, message)
        progressDialogFragment.show(childFragmentManager, "dialog")
    }

    private fun setListeners() {
        binding?.apply {
            loginButton.setOnClickListener {
                if (emailInputText.text?.isNotEmpty() == true && passwordInputText.text?.isNotEmpty() == true)
                    loginWithEmail(
                        emailInputText.text.toString(),
                        passwordInputText.text.toString()
                    )
            }

            loginWithGoogleButton.setOnClickListener {
                loginWithGoogle()
            }

            signInButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
            }
        }

    }

    private fun loginWithGoogle() {
        this.oneTapClient = Identity.getSignInClient(requireActivity())
        val signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    loggingWithGoogleContract.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("TAG", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                Log.d("TAG", e.localizedMessage)
            }


    }

    private fun loginWithEmail(email: String, password: String) {
        onLoadingDialog("Espere un momento", "Iniciando Sesión")

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener(requireActivity()) { task ->
            progressDialogFragment.dismissNow()

            if (task.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    auth.currentUser.toString(),
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(R.id.action_loginFragment_to_hostFeaturesFragment)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Error al iniciar sesión",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

}