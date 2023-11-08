package com.test.testtecnicbzpay.commons.ui

import android.app.Dialog
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.databinding.LayoutCustomProgressDialogBinding


class CustomProgressDialog : DialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance(title: String, message: String): CustomProgressDialog {
            val frag = CustomProgressDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("message", message)
            frag.arguments = args
            return frag
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density: Float = Resources.getSystem().displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            val binding = LayoutCustomProgressDialogBinding.inflate(it.layoutInflater, null, false)
            binding.apply {
                val message = requireArguments().getString("message", "")
                val title = requireArguments().getString("title", "")
                val configuration: Configuration = this@CustomProgressDialog.resources.configuration

                if (title.isNullOrEmpty())
                    loadingTitle.visibility = View.GONE
                else loadingTitle.text = title

                loadingMessage.text = message

                val dialog = MaterialAlertDialogBuilder(it, R.style.AlertMaterialDialogTheme)
                    .setView(binding.root)
                    .setCancelable(false)
                    .create()

                if (configuration.smallestScreenWidthDp >= 600) {
                    dialog.window?.setLayout(dpToPx(384), ViewGroup.LayoutParams.WRAP_CONTENT)
                }

                dialog.setOnKeyListener { arg0, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                    }
                    true
                }
                dialog.setCanceledOnTouchOutside(false)
                return dialog
            }
        }

        return dialog!!

    }
}