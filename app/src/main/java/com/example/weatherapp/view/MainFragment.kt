package com.example.weatherapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.service.autofill.Dataset
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.databinding.MainFragmentBinding
import com.example.weatherapp.model.AppState
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = MainFragmentAdapter()
    private var isDataSetRus: Boolean = true


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            changeWeatherDataSet()
        }
        val observer = Observer<AppState> {renderData(it)}
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }else{
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
        isDataSetRus = !isDataSetRus
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(data: AppState) {
        when (data){
            is AppState.Success -> {
                val weatherData = data.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainFragmentFAB, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){
                        if(isDataSetRus) viewModel.getWeatherFromLocalSourceRus()
                        else viewModel.getWeatherFromLocalSourceWorld()}
                    .show()
            }
        }
    }

//    private fun populateData (weatherData: Weather){
//        with(binding){
//            cityName.text = weatherData.city.city
//            cityCoordinates.text = String.format(
//                getString(R.string.city_coordinates),
//                weatherData.city.lat.toString(),
//                weatherData.city.lon.toString()
//            )
//            temperatureValue.text = weatherData.temperature.toString()
//            feelsLikeValue.text = weatherData.feelsLike.toString()
//        }
//    }


}