package com.test.testtecnicbzpay.features.login.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : BaseFragment() {
    private var binding: FragmentCreateAccountBinding? = null
    private lateinit var auth: FirebaseAuth

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
                onLoadingDialog("Espere un momento", "Creando usuario")

                auth.createUserWithEmailAndPassword(
                    emailInputText.text.toString(),
                    passwordInputText.text.toString()
                ).addOnCompleteListener(requireActivity()) { task ->
                    this@CreateAccountFragment.dismissDialog()

                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            auth.currentUser.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                        findNavController().navigate(R.id.action_createAccountFragment_to_hostFeaturesFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al crear usuario",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }
}