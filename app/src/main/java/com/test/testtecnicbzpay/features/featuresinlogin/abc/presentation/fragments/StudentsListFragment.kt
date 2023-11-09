package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentStudentsListBinding
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.adapters.StudentsListAdapter
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.GetStudentsState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getStudentsList()
    }

    private fun getStudentsList() {
        studentsViewModel.getStudentsList()
        studentsViewModel.getStudentsState.observe(viewLifecycleOwner) {
            validateGetStudentsStatus(it)
        }
    }

    private fun validateGetStudentsStatus(getStudentsState: GetStudentsState) {
        if (getStudentsState.isLoading) {
            onLoadingDialog(
                getString(R.string.wait_moment),
                getString(R.string.get_students_message)
            )
        }

        getStudentsState.studentsList?.let { result: List<StudentEntityDto> ->
            dismissDialog()
            showStudentsList(result)
        }

        if (getStudentsState.error?.isNotEmpty() == true) {
            dismissDialog()
            Toast.makeText(
                requireContext(),
                getStudentsState.error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showStudentsList(students: List<StudentEntityDto>) {
        configBinding(students)
    }

    private fun configBinding(students: List<StudentEntityDto>) {
        this.binding?.apply {
            val adapter = StudentsListAdapter()
            adapter.setStudents(students)
            this.studentsListRecyclerView.adapter = adapter
            this.studentsListRecyclerView.layoutManager =
                LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
        }
    }


    private fun setBinding(inflate: LayoutInflater, container: ViewGroup?) =
        FragmentStudentsListBinding.inflate(inflate, container, false)

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

