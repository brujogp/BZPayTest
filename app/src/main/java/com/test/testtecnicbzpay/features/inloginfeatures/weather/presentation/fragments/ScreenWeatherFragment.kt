package com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentWeatherBinding
import com.test.testtecnicbzpay.features.inloginfeatures.HostActivity
import com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.viewmodels.WeatherViewModel


class ScreenWeatherFragment : BaseFragment(), LocationListener {
    private var binding: FragmentWeatherBinding? = null
    private lateinit var locationManager: LocationManager
    private val weatherViewModel by activityViewModels<WeatherViewModel>()

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
        FragmentWeatherBinding.inflate(inflate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun getLocation() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )) != PackageManager.PERMISSION_GRANTED
        ) {
            (requireActivity() as HostActivity).requestPermission()
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                5f,
                this
            )
        }
    }

    override fun onLocationChanged(location: Location) {
        weatherViewModel.getWeatherAction(
            location.latitude.toString().plus(",").plus(location.longitude.toString())
        )
    }
}