package com.test.testtecnicbzpay.features.inloginfeatures

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.databinding.ActivityHostBinding
import com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.fragments.ScreenWeatherFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {
    private var binding: ActivityHostBinding? = null
    private val locationPermissionCode = 2

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    ?.let { f: Fragment ->
                        f.childFragmentManager.fragments[2]?.let { fragment: Fragment ->
                            (fragment as ScreenWeatherFragment).getLocation()
                        }
                    }


            } else {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }
}