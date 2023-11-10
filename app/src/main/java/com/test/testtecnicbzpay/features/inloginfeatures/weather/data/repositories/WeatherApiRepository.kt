package com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories

import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses.CurrentResponse

interface WeatherApiRepository {
    suspend fun getWeather(location: String): CurrentResponse?
}