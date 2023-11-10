package com.test.testtecnicbzpay.features.inloginfeatures.weather.presentation.states

import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.dtos.WeatherDto

data class WeatherState(
    var isLoading: Boolean = false,
    var data: WeatherDto? = null,
    var error: String? = null
)
