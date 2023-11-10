package com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories

import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.ClientService
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses.CurrentResponse
import javax.inject.Inject

class WeatherApiRepositoryImpl @Inject constructor(
    private val service: ClientService
) : WeatherApiRepository {

    override suspend fun getWeather(location: String): CurrentResponse? {
        return try {
            service.getWeather(location)
        } catch (e: Exception) {
            null
        }

    }
}