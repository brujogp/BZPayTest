package com.test.testtecnicbzpay.features.login.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentLoginBinding
import com.test.testtecnicbzpay.features.HostActivity
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithEmailState
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithGoogleState
import com.test.testtecnicbzpay.features.login.presentation.viewmodels.LoginViewModel

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
        loginViewModel.loggingWithGoogleState.observe(viewLifecycleOwner) { loggingState ->
            validateGoogleSignInStatus(loggingState)
        }
        loginViewModel.loggingWithEmailState.observe(viewLifecycleOwner) { loggingWithEmailStatus ->
            validateEmailLoginStatus(loggingWithEmailStatus)
        }
    }

    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflate, container, false)

    private fun setListeners() {
        binding?.apply {
            loginButton.setOnClickListener {
                if (emailInputText.text?.isNotEmpty() == true && passwordInputText.text?.isNotEmpty() == true)
                    loginViewModel.logInWithEmail(
                        emailInputText.text.toString(),
                        passwordInputText.text.toString(),
                        requireActivity()
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

    private fun validateEmailLoginStatus(loggingWithEmailState: LoggingWithEmailState) {
        if (loggingWithEmailState.isLoading) {
            onLoadingDialog(
                getString(R.string.wait_moment),
                getString(R.string.sign_in_message)
            )
        }

        loggingWithEmailState.user?.let {
            dismissDialog()
            goToLogging()
        }
        if (loggingWithEmailState.error?.isNotEmpty() == true) {
            dismissDialog()
            Toast.makeText(
                requireContext(),
                getString(R.string.sign_in_error_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun validateGoogleSignInStatus(loggingWithGoogleState: LoggingWithGoogleState?) {
        if (loggingWithGoogleState != null) {
            if (loggingWithGoogleState.isLoading) {
                onLoadingDialog(
                    getString(R.string.wait_moment),
                    getString(R.string.sign_in_message)
                )
            }
            loggingWithGoogleState.state?.let {
                loggingWithGoogleContract.launch(loggingWithGoogleState.state)
            }
            if (loggingWithGoogleState.error?.isNotEmpty() == true) {
                Toast
                    .makeText(
                        requireContext(),
                        getString(R.string.sign_in_error_message),
                        Toast.LENGTH_LONG
                    )
                    .show()
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
                                goToLogging()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.sign_in_error_message),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.sign_in_error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    private fun goToLogging() {
        requireActivity().finish()
        startActivity(Intent(requireContext(), HostActivity::class.java))
    }
}