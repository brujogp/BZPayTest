package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentStudentsListBinding
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.StudentsState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.viewmodels.StudentViewModel

class StudentsListFragment : BaseFragment() {
    private var binding: FragmentStudentsListBinding? = null
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
        FragmentStudentsListBinding.inflate(inflate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getStudentsList()
    }

    private fun getStudentsList() {
        studentsViewModel.getStudentsList()
        studentsViewModel.getStudentsState.observe(viewLifecycleOwner) {
            validateStudentsStatus(it)
        }
    }

    private fun validateStudentsStatus(studentsState: StudentsState) {
        if (studentsState.isLoading) {
            onLoadingDialog(
                getString(R.string.wait_moment),
                getString(R.string.get_students_message)
            )
        }
        studentsState.studentsList?.let { result: List<Student> ->
            Log.d("TEST-T", result.toString())
        }

        if (studentsState.error?.isNotEmpty() == true) {
            Toast.makeText(
                requireContext(),
                studentsState.error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}