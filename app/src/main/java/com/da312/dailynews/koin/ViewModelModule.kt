package com.da312.dailynews.koin

import com.da312.dailynews.activity.MainViewModel
import com.da312.dailynews.fragments.AlarmViewModel
import com.da312.dailynews.fragments.NewsViewModel
import com.da312.dailynews.fragments.TodoViewModel
import com.da312.dailynews.fragments.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val module = module {
        viewModel {
            MainViewModel(
            )
        }
        viewModel {
            WeatherViewModel(
                weatherRepository = get(),
                exceptionManager = get()
            )
        }
        viewModel {
            AlarmViewModel(
                alarmRepository = get()
            )
        }
        viewModel {
            NewsViewModel(
                newsRepository = get(),
                exceptionManager = get()
            )
        }
        viewModel {
            TodoViewModel(
                todoRepository = get()
            )
        }
    }
}
