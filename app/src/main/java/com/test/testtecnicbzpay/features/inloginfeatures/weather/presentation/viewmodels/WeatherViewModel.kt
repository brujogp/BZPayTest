package com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.use_cases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    fun getWeatherAction(location: String) {
        viewModelScope.launch {
            getWeatherUseCase.invoke(location).collect() {

            }
        }
    }
}