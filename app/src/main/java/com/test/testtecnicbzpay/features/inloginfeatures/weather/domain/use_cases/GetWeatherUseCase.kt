package com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.use_cases

import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories.WeatherApiRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    val repository: WeatherApiRepository
) {
    operator fun invoke(location: String) = flow {
        emit(ResponseState.Loading())

        val response = repository.getWeather(location)
        emit(ResponseState.Success(""))
    }
}