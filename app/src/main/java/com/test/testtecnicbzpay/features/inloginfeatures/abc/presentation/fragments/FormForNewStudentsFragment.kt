package com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentFormForNewStudentsBinding
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto

class FormForNewStudentsFragment(
    private val onCompleteRegistrationStudent: (StudentEntityDto) -> Unit,
    private val onModifyStudent: (StudentEntityDto) -> Unit
) : BaseFragment() {
    private var studentToModify: StudentEntityDto? = null
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
                if (studentToModify != null) {
                    if (
                        studentNameInputText.text?.isNotEmpty() == true &&
                        studentAgeInputText.text?.isNotEmpty() == true &&
                        subjectInputText.text?.isNotEmpty() == true
                    ) {
                        modifyStudent(studentNameInputText, studentAgeInputText, subjectInputText)
                    }
                } else {
                    if (
                        studentNameInputText.text?.isNotEmpty() == true &&
                        studentAgeInputText.text?.isNotEmpty() == true &&
                        subjectInputText.text?.isNotEmpty() == true
                    ) {
                        registerStudent(studentNameInputText, studentAgeInputText, subjectInputText)
                    }
                }
                clearForm(studentNameInputText, studentAgeInputText, subjectInputText)

                saveStudentButton.text = "Registrar estudiante"
                studentToModify = null
            }
        }
    }

    private fun modifyStudent(
        studentNameInputText: TextInputEditText,
        studentAgeInputText: TextInputEditText,
        subjectInputText: TextInputEditText
    ) {
        studentToModify?.let {
            this.onModifyStudent.invoke(
                StudentEntityDto(
                    id = it.id,
                    studentName = studentNameInputText.text.toString(),
                    studentAge = studentAgeInputText.text.toString().toInt(),
                    subject = subjectInputText.text.toString()
                )
            )
        }
    }

    private fun registerStudent(
        studentNameInputText: TextInputEditText,
        studentAgeInputText: TextInputEditText,
        subjectInputText: TextInputEditText
    ) {
        this.onCompleteRegistrationStudent.invoke(
            StudentEntityDto(
                studentName = studentNameInputText.text.toString(),
                studentAge = studentAgeInputText.text.toString().toInt(),
                subject = subjectInputText.text.toString()
            )
        )
    }

    private fun clearForm(
        studentNameInputText: TextInputEditText,
        studentAgeInputText: TextInputEditText,
        subjectInputText: TextInputEditText
    ) {
        studentNameInputText.text?.clear()
        studentNameInputText.clearFocus()
        studentAgeInputText.text?.clear()
        studentAgeInputText.clearFocus()
        subjectInputText.text?.clear()
        subjectInputText.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun editStudent(student: StudentEntityDto) {
        this.studentToModify = student
        this.binding?.apply {
            studentNameInputText.setText(student.studentName)
            studentAgeInputText.setText(student.studentAge.toString())
            subjectInputText.setText(student.subject)
            saveStudentButton.text = "Modificar estudiante"
        }
    }
}