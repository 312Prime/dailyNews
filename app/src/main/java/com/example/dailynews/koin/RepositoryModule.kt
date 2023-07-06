package com.example.dailynews.koin

import com.example.dailynews.data.repository.NewsRepository
import com.example.dailynews.data.repository.WeatherRepository
import org.koin.dsl.module

object RepositoryModule {
    val module = module {
        single {
            WeatherRepository(
                restfulManager = get()
            )
        }
        single {
            NewsRepository(
                restfulManager = get()
            )
        }
    }
}