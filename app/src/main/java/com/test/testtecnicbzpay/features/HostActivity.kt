package com.test.testtecnicbzpay.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.testtecnicbzpay.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {
    private var binding: ActivityHostBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setBinding().root)
    }

    private fun setBinding() = ActivityHostBinding.inflate(layoutInflater)

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}