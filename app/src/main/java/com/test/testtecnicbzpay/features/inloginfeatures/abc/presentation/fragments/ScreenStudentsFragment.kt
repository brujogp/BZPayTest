package com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.fragments

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
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.states.ActionWithStudentState
import com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.viewmodels.StudentViewModel

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
                FormForNewStudentsFragment(
                    onCompleteRegistrationStudent = { newStudent: StudentEntityDto ->
                        registerNewStudent(newStudent)
                    },
                    onModifyStudent = { studentToModify: StudentEntityDto ->
                        modifyStudent(studentToModify)
                    }
                )

            this@ScreenStudentsFragment.studentsListFragment =
                StudentsListFragment(
                    onEditStudent = { student ->
                        formStudentsFragment.editStudent(student)
                    },
                    onDeleteStudent = { student ->
                        deleteStudent(student)
                    }
                )

            configFragments(formForNewStudentsContainer, studentsListContainer)
        }
    }

    private fun deleteStudent(student: StudentEntityDto) {
        this.studentsViewModel.deleteStudentAction(student)
        this.studentsViewModel.deleteStudent.observe(viewLifecycleOwner) {
            validateActionOnStudentState(it, true)
        }
    }

    private fun modifyStudent(studentToModify: StudentEntityDto) {
        studentsViewModel.modifyStudentAction(studentToModify)
        studentsViewModel.modifyStudent.observe(viewLifecycleOwner) {
            validateActionOnStudentState(it)
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
        studentsViewModel.registerNewStudentAction(newStudent)
        studentsViewModel.registerNewStudent.observe(viewLifecycleOwner) {
            validateActionOnStudentState(it)
        }
    }

    private fun validateActionOnStudentState(
        actionWithStudentState: ActionWithStudentState,
        isDeleteStudent: Boolean = false
    ) {
        if (actionWithStudentState.isSuccess) {
            dismissDialog()


            if (isDeleteStudent) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.delete_student_success),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.new_student_registered),
                    Toast.LENGTH_LONG
                ).show()
            }

            deleteObservers()

            this.studentsListFragment.getStudents()
        }
        if (actionWithStudentState.error?.isNotEmpty() == true) {
            dismissDialog()

            Toast.makeText(
                requireContext(),
                actionWithStudentState.error,
                Toast.LENGTH_LONG
            ).show()

            deleteObservers()
        }
    }

    private fun deleteObservers() {
        studentsViewModel.modifyStudent.removeObservers(viewLifecycleOwner)
        studentsViewModel.registerNewStudent.removeObservers(viewLifecycleOwner)
        studentsViewModel.deleteStudent.removeObservers(viewLifecycleOwner)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}