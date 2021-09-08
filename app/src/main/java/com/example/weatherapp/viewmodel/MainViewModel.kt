package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.AppState
import com.example.weatherapp.model.repository.Repository
import com.example.weatherapp.model.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel (private val repository : Repository = RepositoryImpl()):
    ViewModel() {
    private val liveDataToObserve : MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussia = false)
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussia = true)

    private fun getDataFromLocalSource(isRussia :Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1500)
            liveDataToObserve.postValue(
                AppState.Success(if (isRussia)
                    repository.getWeatherFromLocalStorageRus()
                    else repository.getWeatherFromLocalStorageWorld()
                )
            )
        }.start()
    }
}