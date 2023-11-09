package com.test.testtecnicbzpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.testtecnicbzpay.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = setBinding().also {
            setContentView(it.root)
        }
    }

    private fun setBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}