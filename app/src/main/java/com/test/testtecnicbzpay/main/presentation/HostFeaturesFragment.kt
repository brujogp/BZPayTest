package com.test.testtecnicbzpay.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.testtecnicbzpay.databinding.FragmentNavigationBarBinding

class HostFeaturesFragment : Fragment() {
    private var binding: FragmentNavigationBarBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = setBinding(inflater, container)
        return this.binding!!.root
    }

    private fun setBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentNavigationBarBinding.inflate(inflater, container, false)

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}