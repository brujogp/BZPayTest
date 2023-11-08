package com.test.testtecnicbzpay.features.login.presentation.screens

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentLoginBinding
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingState
import com.test.testtecnicbzpay.features.login.presentation.viewmodels.LoginViewModel
import kotlin.math.log

class LoginFragment : BaseFragment() {
    private lateinit var oneTapClient: SignInClient
    private var binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private val loginViewModel by activityViewModels<LoginViewModel>()


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
        setObservers()
    }

    private fun setObservers() {
        loginViewModel.loggingState.observe(viewLifecycleOwner) { loggingState ->
            verifyLoginState(loggingState)
        }
    }

    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflate, container, false)

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
                this@LoginFragment.oneTapClient = Identity.getSignInClient(requireActivity())
                loginViewModel.signInWithGoogle(requireActivity())
            }
            signInButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
            }
        }
    }

    private fun verifyLoginState(loggingState: LoggingState?) {
        if (loggingState != null) {
            if (loggingState.isLoading) {
                onLoadingDialog("Espere un momento", "Iniciando Sesión")
            }
            loggingState.state?.let {
                loggingWithGoogleContract.launch(loggingState.state)
            }
            if (loggingState.error?.isNotEmpty() == true) {
                Toast
                    .makeText(requireContext(), "No se pudo iniciar sesión", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        onLoadingDialog("Espere un momento", "Iniciando Sesión")

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener(requireActivity()) { task ->
            dismissDialog()

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
                                findNavController().navigate(R.id.action_loginFragment_to_hostFeaturesFragment)
                                Toast.makeText(
                                    requireContext(),
                                    auth.currentUser.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Error al iniciar sesión",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Error al iniciar sesión",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
}