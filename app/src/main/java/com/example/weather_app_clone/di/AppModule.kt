package com.example.weather_app_clone.di

import com.example.weather_app_clone.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesWeatherService(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideMyRepository(
//        api: WeatherApi,
//        app: MyApp,
//        @Named("hello1") hello: String
//    ): MyRepository {
//        return MyRepositoryImpl(api, app)
//    }

//    @Provides
//    @Singleton
//    @Named("hello1")
//    fun provideString1() = "Hello 1"
//
//    @Provides
//    @Singleton
//    @Named("hello2")
//    fun provideString2() = "Hello 2"
}