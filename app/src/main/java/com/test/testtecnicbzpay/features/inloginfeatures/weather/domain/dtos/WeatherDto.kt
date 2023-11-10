package com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.dtos

data class WeatherDto(
    val region: String,
    val date: String,
    val tempC: String,
    val weatherState: String,
    val urlIcon: String

)
