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

    private var counter : Int = 0

    fun getData(): LiveData<AppState>{
        return liveDataToObserve
    }

    fun getWeatherFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            counter++
            liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
        }.start()
    }
    fun getWeatherFromRemoteSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            counter++
            liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromServer()))
        }.start()
    }
}