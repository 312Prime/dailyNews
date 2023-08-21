package com.da312.dailynews.koin

import com.da312.dailynews.data.repository.AlarmRepository
import com.da312.dailynews.data.repository.NewsRepository
import com.da312.dailynews.data.repository.TodoRepository
import com.da312.dailynews.data.repository.WeatherRepository
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
        single {
            AlarmRepository(
                sharedPreferenceManager = get()
            )
        }
        single {
            TodoRepository(
                sharedPreferenceManager = get()
            )
        }
    }
}