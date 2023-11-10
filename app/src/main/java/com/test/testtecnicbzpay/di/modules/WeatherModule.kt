package com.test.testtecnicbzpay.di.modules

import com.google.gson.GsonBuilder
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories.WeatherApiRepository
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.repositories.WeatherApiRepositoryImpl
import com.test.testtecnicbzpay.features.inloginfeatures.weather.data.web_client.ClientService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(service: ClientService): WeatherApiRepository =
        WeatherApiRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideRetrofitRepository(): ClientService {
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .followRedirects(true)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()

                val customUrl = HttpUrl.Builder()
                customUrl.host(chain.request().url().host())
                customUrl.encodedPath(chain.request().url().encodedPath())
                customUrl.scheme(chain.request().url().scheme())

                for (e: String in chain.request().url().queryParameterNames()) {
                    val queryValue: List<String?> =
                        chain.request().url().queryParameterValues(e)

                    customUrl.addQueryParameter(e, queryValue[0])
                }

                customUrl.addQueryParameter("key", "39580c9a44f34b7e99564534230911")

                request.url(customUrl.build())

                chain.proceed(request.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()
            .create(ClientService::class.java)
    }
}