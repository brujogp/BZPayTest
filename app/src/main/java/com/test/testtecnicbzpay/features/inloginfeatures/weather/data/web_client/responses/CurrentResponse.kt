package com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses

import com.google.gson.annotations.SerializedName
import com.test.testtecnicbzpay.features.inloginfeatures.weather.domain.dtos.WeatherDto
import kotlinx.serialization.*
import kotlinx.serialization.SerialName


data class CurrentResponse(
    val location: Location? = null,
    val current: Current? = null
)

data class Current(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long? = null,

    @SerializedName("last_updated")
    val lastUpdated: String? = null,

    @SerializedName("temp_c")
    val tempC: Double? = null,

    @SerializedName("temp_f")
    val tempF: Double? = null,

    @SerializedName("is_day")
    val isDay: Double? = null,

    val condition: Condition? = null,

    @SerializedName("wind_mph")
    val windMph: Double? = null,

    @SerializedName("wind_kph")
    val windKph: Double? = null,

    @SerializedName("wind_degree")
    val windDegree: Double? = null,

    @SerializedName("wind_dir")
    val windDir: String? = null,

    @SerializedName("pressure_mb")
    val pressureMB: Double? = null,

    @SerializedName("pressure_in")
    val pressureIn: Double? = null,

    @SerializedName("precip_mm")
    val precipMm: Double? = null,

    @SerializedName("precip_in")
    val precipIn: Double? = null,

    val humidity: Double? = null,
    val cloud: Double? = null,

    @SerializedName("feelslike_c")
    val feelslikeC: Double? = null,

    @SerializedName("feelslike_f")
    val feelslikeF: Double? = null,

    @SerializedName("vis_km")
    val visKM: Long? = null,

    @SerializedName("vis_miles")
    val visMiles: Long? = null,

    val uv: Long? = null,

    @SerializedName("gust_mph")
    val gustMph: Double? = null,

    @SerializedName("gust_kph")
    val gustKph: Double? = null,

    @SerializedName("air_quality")
    val airQuality: Map<String, Double>? = null
)

data class Condition(
    val text: String? = null,
    val icon: String? = null,
    val code: Long? = null
)

data class Location(
    val name: String? = null,
    val region: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,

    @SerializedName("tz_id")
    val tzID: String? = null,

    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long? = null,

    val localtime: String? = null
)

fun CurrentResponse.convertToDto(): WeatherDto {
    return WeatherDto(
        region = location!!.region!!,
        date = location.name!!,
        tempC = current!!.tempC!!.toString().substring(0, 2),
        weatherState = current.condition!!.text!!,
        urlIcon = current.condition.icon!!
    )
}