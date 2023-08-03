package com.example.dailynews.koin

import com.example.dailynews.activity.MainViewModel
import com.example.dailynews.fragments.AlarmViewModel
import com.example.dailynews.fragments.NewsViewModel
import com.example.dailynews.fragments.TodoViewModel
import com.example.dailynews.fragments.WeatherViewModel
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
