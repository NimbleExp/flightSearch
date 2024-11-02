package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.AppDatabase

class AirportApplication: Application() {
    val database: AppDatabase by lazy{
        AppDatabase.getDatabase(this)
    }
}