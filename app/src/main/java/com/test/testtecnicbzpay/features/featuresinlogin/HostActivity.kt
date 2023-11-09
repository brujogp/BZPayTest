package com.test.testtecnicbzpay.features.featuresinlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {
    private var binding: ActivityHostBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = setBinding().also {
            setContentView(it.root)
        }

        configBinding()
    }

    private fun setBinding() = ActivityHostBinding.inflate(layoutInflater)

    private fun configBinding() {
        this.binding?.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            binding?.mainBottomNavigation?.setupWithNavController(navController)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}