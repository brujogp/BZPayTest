package com.test.testtecnicbzpay.commons.presentation

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    private lateinit var progressDialogFragment: CustomProgressDialog
    fun onLoadingDialog(title: String, message: String) {
        this.progressDialogFragment = CustomProgressDialog.newInstance(title, message)
        progressDialogFragment.show(childFragmentManager, "dialog")
    }

    fun dismissDialog() {
        childFragmentManager.fragments.filter { fragmentsList ->
            fragmentsList.tag == LOADING_DIALOG_FRAGMENT_TAG
        }.let { dialogsFragments ->
            dialogsFragments.forEach { dialogFragment ->
                (dialogFragment as CustomProgressDialog).dismissNow()
            }
        }
    }

    companion object {
        const val LOADING_DIALOG_FRAGMENT_TAG = "dialog"
    }
}