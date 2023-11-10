package com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.responses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.*
import kotlinx.serialization.SerialName


data class CurrentResponse(
    val location: Location? = null,
    val current: Current? = null
)

data class Current(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long? = null,

    @SerialName("last_updated")
    val lastUpdated: String? = null,

    @SerialName("temp_c")
    val tempC: Double? = null,

    @SerialName("temp_f")
    val tempF: Double? = null,

    @SerialName("is_day")
    val isDay: Long? = null,

    val condition: Condition? = null,

    @SerialName("wind_mph")
    val windMph: Double? = null,

    @SerialName("wind_kph")
    val windKph: Double? = null,

    @SerialName("wind_degree")
    val windDegree: Long? = null,

    @SerialName("wind_dir")
    val windDir: String? = null,

    @SerialName("pressure_mb")
    val pressureMB: Long? = null,

    @SerialName("pressure_in")
    val pressureIn: Double? = null,

    @SerialName("precip_mm")
    val precipMm: Long? = null,

    @SerialName("precip_in")
    val precipIn: Long? = null,

    val humidity: Long? = null,
    val cloud: Long? = null,

    @SerialName("feelslike_c")
    val feelslikeC: Long? = null,

    @SerialName("feelslike_f")
    val feelslikeF: Double? = null,

    @SerialName("vis_km")
    val visKM: Long? = null,

    @SerialName("vis_miles")
    val visMiles: Long? = null,

    val uv: Long? = null,

    @SerialName("gust_mph")
    val gustMph: Double? = null,

    @SerialName("gust_kph")
    val gustKph: Double? = null,

    @SerialName("air_quality")
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

    @SerialName("tz_id")
    val tzID: String? = null,

    @SerialName("localtime_epoch")
    val localtimeEpoch: Long? = null,

    val localtime: String? = null
)