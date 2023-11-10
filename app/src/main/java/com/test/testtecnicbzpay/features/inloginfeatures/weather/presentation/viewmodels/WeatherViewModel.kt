package com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.states.ActionWithStudentState
import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.dtos.WeatherDto
import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.use_cases.GetWeatherUseCase
import com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _getWeather = MutableLiveData<WeatherState>()
    val getWeather: LiveData<WeatherState> = _getWeather

    fun getWeatherAction(location: String) {
        viewModelScope.launch {
            getWeatherUseCase.invoke(location)
                .collect { responseWeather: ResponseState<WeatherDto> ->
                    when (responseWeather) {
                        is ResponseState.Loading -> {
                            _getWeather.value = WeatherState(isLoading = true)
                        }

                        is ResponseState.Success -> {
                            _getWeather.value = WeatherState(data = responseWeather.data!!)
                        }

                        is ResponseState.Error -> {
                            _getWeather.value = WeatherState(error = responseWeather.message)

                        }
                    }
                }
        }
    }
}