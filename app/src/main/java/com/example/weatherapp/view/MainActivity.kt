package com.example.weatherapp.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp.ConnectivityBroadcastReceiver
import com.example.weatherapp.R
import com.example.weatherapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val connectivityReceiver = ConnectivityBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
        registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onDestroy() {
        unregisterReceiver(connectivityReceiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}