package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentFormForNewStudentsBinding
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto

class FormForNewStudentsFragment(
    private val onCompleteRegistrationStudent: (StudentEntityDto) -> Unit
) : BaseFragment() {
    private var binding: FragmentFormForNewStudentsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = setBinding(inflater, container)
        this.binding?.let {
            return it.root
        } ?: run {
            return null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configBinding()
    }

    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentFormForNewStudentsBinding.inflate(inflate, container, false)

    private fun configBinding() {
        this.binding?.apply {
            saveStudentButton.setOnClickListener {
                this@FormForNewStudentsFragment.onCompleteRegistrationStudent.invoke(
                    StudentEntityDto(
                        studentName = studentNameInputText.text.toString(),
                        studentAge = studentAgeInputText.text.toString().toInt(),
                        subject = subjectInputText.text.toString()
                    )
                )

                studentNameInputText.text?.clear()
                studentAgeInputText.text?.clear()
                subjectInputText.text?.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}