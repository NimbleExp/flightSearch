package com.example.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val INPUT_STRING = stringPreferencesKey("input_String")
        const val TAG = "UserPreferencesRe"
    }

    val inputString: Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error reading preferences. ", it)
            }
            else {
                throw it
            }
        }
        .map { preference ->
            preference[INPUT_STRING]?:""
        }

    suspend fun  saveInputPreference(inputString: String){
        dataStore.edit { preference ->
            preference[INPUT_STRING] = inputString
        }
    }
}