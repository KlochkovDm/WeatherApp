package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.MainActivityBinding
import com.example.weatherapp.databinding.MainFragmentBinding
import com.example.weatherapp.databinding.MainRecyclerItemBinding
import com.example.weatherapp.model.data.Weather
import com.example.weatherapp.model.data.getRussianCities
import com.example.weatherapp.model.repository.Repository

class MainFragmentAdapter:
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData : List<Weather> = listOf()

    fun setWeather(data: List<Weather>){
        weatherData = data
        notifyDataSetChanged()
    }

    inner class MainViewHolder(val binding: MainRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind (weather: Weather) {
            binding.mainFragmentRecyclerItemTextView.text = weather.city.city
            binding.root.setOnClickListener {
                Toast.makeText(itemView.context,weather.city.city,Toast.LENGTH_LONG).show()
            }
//            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.city
//            itemView.setOnClickListener{
//                Toast.makeText(itemView.context,weather.city.city,Toast.LENGTH_LONG).show()
//            }
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