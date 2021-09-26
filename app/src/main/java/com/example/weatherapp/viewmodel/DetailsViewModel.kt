package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.AppState
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.model.data.convertDtoToModel
import com.example.weatherapp.model.data.dto.FactDTO
import com.example.weatherapp.model.data.dto.WeatherDTO
import com.example.weatherapp.model.data.getDefaultCity
import com.example.weatherapp.model.repository.DetailsRepository
import com.example.weatherapp.model.repository.DetailsRepositoryImpl
import com.example.weatherapp.model.repository.RemoteDataSource
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

private const val CORRUPTED_DATA = "Неполгные данные"
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"

class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepository : DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    fun getLiveData() = detailsLiveData

    fun getWeatherFromRemoteSource(requestLink : String){
        detailsLiveData.value = AppState.Loading
        detailsRepository.getWeatherDetailsFromServer(requestLink, callback)
    }

    private val callback = object : Callback{
        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            val serverResponse = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else{
                    AppState.Error(Throwable(SERVER_ERROR ))
                }
            )
        }

        override fun onFailure(call: Call, e: IOException) {
            detailsLiveData.postValue(AppState.Error(Throwable(e.message ?: REQUEST_ERROR)))
        }
    }

    fun checkResponse (serverResponse: String) : AppState{
        val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
        val fact: FactDTO? = weatherDTO.fact
        return if (fact?.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()) {
                    AppState.Error(Throwable(CORRUPTED_DATA))
                } else {
                    AppState.Success(convertDtoToModel(weatherDTO))
                }
    }


}