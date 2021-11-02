package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.app.App
import com.example.weatherapp.app.App.Companion.getHistoryDao
import com.example.weatherapp.model.AppState
import com.example.weatherapp.model.repository.LocalRepository
import com.example.weatherapp.model.repository.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
): ViewModel() {
    fun getAllHistory(){
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }
}