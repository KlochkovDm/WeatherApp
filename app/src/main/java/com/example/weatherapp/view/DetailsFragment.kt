package com.example.weatherapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DetailsFragmentBinding
import com.example.weatherapp.model.AppState
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.viewmodel.DetailsViewModel
import okhttp3.*

private const val TEMP_INVALID = -100
private const val FEELS_LIKE_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"
private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class DetailsFragment: Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    private val viewModel : DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }


    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                binding.main.show()
                binding.loadingLayout.hide()
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.main.hide()
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.main.show()
                binding.loadingLayout.hide()
                binding.main.showSnackBar(getString(R.string.error), getString(R.string.reload)) {
                    viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat,weatherBundle.city.lon)
                }
            }
        }
    }

    private fun setWeather(weather: Weather){
        with(binding){
            weatherBundle.city.let { city ->
                cityName.text = city.city
                cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
                )
            }
            weather.let {
                temperatureValue.text = it.temperature.toString()
                feelsLikeValue.text = it.feelsLike.toString()
                weatherCondition.text = it.condition
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?: Weather()
        viewModel.detailsLiveData.observe(viewLifecycleOwner, {renderData(it)})
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat,weatherBundle.city.lon)
    }

    companion object{
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment{
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}