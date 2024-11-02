package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context, AppDatabase::class.java, "flight_search.db"
                )
                    .createFromAsset("database/flight_search.db")
                    .addTypeConverter(AirportConverter())
                    .build().also {
                        Instance = it
                    }
            }
        }

    }
}
