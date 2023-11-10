package com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.test.testtecnicbzpay.R
import com.test.testtecnicbzpay.commons.presentation.BaseFragment
import com.test.testtecnicbzpay.databinding.FragmentWeatherBinding
import com.test.testtecnicbzpay.features.inloginfeatures.HostActivity
import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.dtos.WeatherDto
import com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.states.WeatherState
import com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.viewmodels.WeatherViewModel


class ScreenWeatherFragment : BaseFragment(), LocationListener {
    private lateinit var location: Location
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

        loading()
    }

    override fun onLocationChanged(location: Location) {
        this.location = location
        requestWeather()
    }

    private fun requestWeather() {
        weatherViewModel.getWeatherAction(
            location.latitude.toString().plus(",").plus(location.longitude.toString())
        )
        weatherViewModel.getWeather.observe(viewLifecycleOwner) {
            validateWeatherStatus(it)
        }
    }


    private fun loading() {
        onLoadingDialog(
            getString(R.string.wait_moment),
            getString(R.string.getting_weather_info)
        )
    }

    private fun validateWeatherStatus(weatherState: WeatherState) {
        weatherState.data?.let {
            dismissDialog()
            setWeatherView(it)
            weatherViewModel.getWeather.removeObservers(viewLifecycleOwner)
        }

        if (weatherState.error?.isNotEmpty() == true) {
            dismissDialog()
            binding?.container?.visibility = View.VISIBLE
            Toast.makeText(
                requireContext(),
                getString(R.string.cannot_get_weather_info),
                Toast.LENGTH_LONG
            ).show()
            weatherViewModel.getWeather.removeObservers(viewLifecycleOwner)
        }
    }

    private fun setWeatherView(weatherInfo: WeatherDto) {
        binding?.apply {

            container.visibility = View.VISIBLE

            weatherStateTextView.text = weatherInfo.weatherState
            weatherLocationTextView.text = weatherInfo.region
            weatherDateTextView.text = weatherInfo.date
            weatherTemperatureDegreesCentigradeTextView.text = weatherInfo.tempC.plus("°")

            Glide
                .with(this@ScreenWeatherFragment)
                .load("https:".plus(weatherInfo.urlIcon))
                .centerCrop()
                .into(weatherImage);


            updateWeatherButton.setOnClickListener {
                requestWeather()
                loading()
            }
        }
    }
}