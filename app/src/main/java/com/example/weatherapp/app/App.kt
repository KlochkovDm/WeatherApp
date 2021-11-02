package com.example.weatherapp.app

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.room.HistoryDao
import com.example.weatherapp.room.HistoryDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao{
            if (db == null) {
                synchronized(HistoryDataBase::class.java){
                    if (db == null){
                        if (appInstance == null) {
                            throw IllegalAccessException("Application ids null while cheating database")
                        }
                    }
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDataBase::class.java,
                        DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.historyDao()
        }
    }
}