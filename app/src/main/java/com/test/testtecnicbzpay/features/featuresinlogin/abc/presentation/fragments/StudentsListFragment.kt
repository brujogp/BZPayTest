package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentStudentsListBinding

class StudentsListFragment : BaseFragment() {
    private var binding: FragmentStudentsListBinding? = null

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

