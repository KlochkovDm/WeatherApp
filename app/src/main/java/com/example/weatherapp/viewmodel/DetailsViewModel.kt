package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.app.App.Companion.getHistoryDao
import com.example.weatherapp.model.AppState
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.model.data.convertDtoToModel
import com.example.weatherapp.model.data.dto.FactDTO
import com.example.weatherapp.model.data.dto.WeatherDTO
import com.example.weatherapp.model.repository.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

private const val CORRUPTED_DATA = "Неполгные данные"
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepository : DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getWeatherFromRemoteSource(lat:Double, lon: Double){
        detailsLiveData.value = AppState.Loading
        detailsRepository.getWeatherDetailsFromServer(lat, lon, callback)
    }

    fun saveCityToDB(weather:Weather){
        historyRepository.saveEntity(weather)
    }

    private val callback = object : Callback<WeatherDTO> {
        @Throws(IOException::class)
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else{
                    AppState.Error(Throwable(SERVER_ERROR ))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    fun checkResponse (serverResponse: WeatherDTO) : AppState{

        val fact: FactDTO? = serverResponse.fact
        return if (fact?.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()) {
                    AppState.Error(Throwable(CORRUPTED_DATA))
                } else {
                    AppState.Success(convertDtoToModel(serverResponse))
                }
    }


}