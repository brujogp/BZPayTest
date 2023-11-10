package com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.use_cases

import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories.WeatherApiRepository
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses.CurrentResponse
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses.convertToDto
import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.dtos.WeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    val repository: WeatherApiRepository
) {
    operator fun invoke(location: String): Flow<ResponseState<WeatherDto>> = flow {
        emit(ResponseState.Loading())

        val response: CurrentResponse? = repository.getWeather(location)
        response?.let {
            emit(ResponseState.Success(response.convertToDto()))
        } ?: run {
            emit(ResponseState.Error("Error al obtener la información"))
        }
    }
}