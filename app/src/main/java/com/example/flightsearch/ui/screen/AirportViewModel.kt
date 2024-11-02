package com.example.flightsearch.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.AirportApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportDao
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteDao
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AirportViewModel(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
): ViewModel() {

    var isActive by  mutableStateOf(false)

    var flightSearchUi by mutableStateOf(FlightSearchUi())
        private set

    var favoriteUi by mutableStateOf(FavoriteUi())
        private set

    var userInput by mutableStateOf("")
        private set

    fun updateSearch(input: String){
        userInput = input
    }

    fun updateCurrentAirport(airport: Airport?){
        flightSearchUi = flightSearchUi.copy(
            currentAirport = airport
        )
    }

    fun getSearchListAirport(input: String){
        viewModelScope.launch {
            flightSearchUi = flightSearchUi.copy(
                suggestedAirportList = airportDao
                    .getAirportByName("%$input%")
                    .filterNotNull()
                    .first()
            )
        }
    }

    fun addOrRemoveFavorite(favorite: Favorite){
        viewModelScope.launch {
            if (favoriteDao.getFavorite(favorite.departureAirport, favorite.destinationAirport) == null){
                addFavorite(favorite)
            } else {
                removeFavorite(favoriteDao.getFavorite(favorite.departureAirport, favorite.destinationAirport)!!)
            }
            updateFavorites()
        }
    }

    private fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteDao.addFavorite(favorite)
        }
    }


    private fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteDao.removeFavorite(favorite)
        }
    }

    fun updateFavorites(){
        viewModelScope.launch {
            favoriteUi = favoriteUi.copy(
                favorites = favoriteDao.getAll().filterNotNull().first()
            )
        }
    }

    fun getAllDestinationAirports(){
        viewModelScope.launch {
            if(flightSearchUi.currentAirport != null){
                flightSearchUi = flightSearchUi.copy(
                    destinationAirportList = airportDao.getAllByPassengers(flightSearchUi.currentAirport!!.id)
                        .filterNotNull()
                        .first()
                )
            }
        }
    }

    fun isFavorite(departureCode: String, destinationCode: String): Boolean {
        if (favoriteUi.favorites.isNotEmpty()) {
            favoriteUi.favorites.forEach { favorite ->
                if (favorite.departureAirport.iataCode == departureCode && favorite.destinationAirport.iataCode == destinationCode) return true
            }
        }
        return false
    }




    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AirportApplication)
                AirportViewModel(application.database.airportDao(), application.database.favoriteDao())
            }
        }
    }


}

data class FlightSearchUi(
    val currentAirport: Airport? = null,
    val suggestedAirportList: List<Airport> = emptyList(),
    val destinationAirportList: List<Airport> = emptyList(),
)

data class FavoriteUi(
    val favorites: List<Favorite> = listOf(),
)