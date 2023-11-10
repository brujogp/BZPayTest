package com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories

import android.util.Log
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.ClientService
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses.CurrentResponse
import javax.inject.Inject

class WeatherApiRepositoryImpl @Inject constructor(
    private val service: ClientService
) : WeatherApiRepository {

    override suspend fun getWeather(location: String): CurrentResponse? {
        return try {
            val response = service.getWeather(location = location)
            response
        } catch (e: Exception) {
            Log.w("TEST-T", e.printStackTrace().toString())
            null
        }
    }
}