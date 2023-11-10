package com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client

import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses.CurrentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientService {
    @GET("current.json")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("lang") lang: String = "es"
    ): CurrentResponse
}