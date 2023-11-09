package com.test.testtecnicbzpay.features.login.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentCreateAccountBinding
import com.test.testtecnicbzpay.features.HostActivity
import com.test.testtecnicbzpay.features.login.presentation.states.LoggingWithEmailState
import com.test.testtecnicbzpay.features.login.presentation.viewmodels.LoginViewModel

class CreateAccountFragment : BaseFragment() {
    private var binding: FragmentCreateAccountBinding? = null
    private lateinit var auth: FirebaseAuth
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        this.binding = setBinding(inflater, container)
        return this.binding!!.root
    }

    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentCreateAccountBinding.inflate(inflate, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            createAccountButton.setOnClickListener {
                createAccount(
                    emailInputText.text.toString(),
                    passwordInputText.text.toString()
                )
            }
        }

        setObservers()
    }

    private fun setObservers() {
        loginViewModel.signInWithEmailState.observe(viewLifecycleOwner) {
            validateEmailSignInStatus(it)
        }
    }

    private fun validateEmailSignInStatus(signInStatus: LoggingWithEmailState) {
        if (signInStatus.isLoading) {
            onLoadingDialog(
                getString(R.string.wait_moment),
                getString(R.string.sign_in_message)
            )
        }

        signInStatus.user?.let {
            dismissDialog()
            goToLogging()
        }

        if (signInStatus.error?.isNotEmpty() == true) {
            dismissDialog()
            Toast.makeText(
                requireContext(),
                getString(R.string.sign_in_error_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun createAccount(email: String, password: String) {
        loginViewModel.signInWithEmail(email, password, requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    private fun goToLogging() {
        requireActivity().finish()
        startActivity(Intent(requireContext(), HostActivity::class.java))
    }
}