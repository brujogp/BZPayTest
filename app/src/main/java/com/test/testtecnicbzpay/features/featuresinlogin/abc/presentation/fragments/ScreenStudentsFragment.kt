package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentStudentsScreenBinding
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.RegisterNewStudentState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.viewmodels.StudentViewModel

class ScreenStudentsFragment : BaseFragment() {
    private lateinit var formStudentsFragment: FormForNewStudentsFragment
    private lateinit var studentsListFragment: StudentsListFragment

    private var binding: FragmentStudentsScreenBinding? = null
    private val studentsViewModel by activityViewModels<StudentViewModel>()

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

    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentStudentsScreenBinding.inflate(inflate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configBinding()
    }

    private fun configBinding() {
        binding?.apply {
            this@ScreenStudentsFragment.formStudentsFragment =
                FormForNewStudentsFragment { newStudent: StudentEntityDto ->
                    registerNewStudent(newStudent)
                }

            this@ScreenStudentsFragment.studentsListFragment =
                StudentsListFragment()

            configFragments(formForNewStudentsContainer, studentsListContainer)
        }
    }

    private fun configFragments(
        formForNewStudentsContainer: FrameLayout,
        studentsListContainer: FrameLayout
    ) {
        parentFragmentManager
            .beginTransaction()
            .replace(
                formForNewStudentsContainer.id,
                this.formStudentsFragment
            )
            .commit()

        parentFragmentManager
            .beginTransaction()
            .replace(
                studentsListContainer.id,
                this.studentsListFragment
            )
            .commit()
    }

    private fun registerNewStudent(newStudent: StudentEntityDto) {
        studentsViewModel.registerNewStudent(newStudent)
        studentsViewModel.registerNewStudent.observe(viewLifecycleOwner) {
            validateRegisterNewStudentState(it)
        }
    }

    private fun validateRegisterNewStudentState(registerNewStudentState: RegisterNewStudentState) {
        if (registerNewStudentState.isLoading) {
            onLoadingDialog(
                getString(R.string.wait_moment),
                getString(R.string.get_students_message)
            )
        }
        if (registerNewStudentState.isSuccess) {
            dismissDialog()
            studentsViewModel.getStudentsList()
            Toast.makeText(
                requireContext(),
                getString(R.string.new_student_registered),
                Toast.LENGTH_LONG
            ).show()
        }
        if (registerNewStudentState.error?.isNotEmpty() == true) {
            dismissDialog()
            Toast.makeText(
                requireContext(),
                registerNewStudentState.error,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}