package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.MainRecyclerItemBinding
import com.example.weatherapp.model.data.Weather

class MainFragmentAdapter:
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData : List<Weather> = listOf()

    private var onItemViewClickListener: (Weather) -> Unit = {}

    fun setOnItemViewClickListener(onItemClickedListener: (Weather) -> Unit){
        this.onItemViewClickListener = onItemClickedListener
    }

    fun setWeather(data: List<Weather>){
        weatherData = data
        notifyDataSetChanged()
    }

    inner class MainViewHolder(val binding: MainRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind (weather: Weather) {
            binding.apply{
                mainFragmentRecyclerItemTextView.text = weather.city.city
                root.setOnClickListener {
                    onItemViewClickListener(weather)
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }
}