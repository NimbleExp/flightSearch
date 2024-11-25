package com.example.flightsearch.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AirportViewModel = viewModel(factory = AirportViewModel.factory)
){
    Scaffold(
        topBar = {
            if (!viewModel.isActive && viewModel.userInput == "") {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Flight Search")
                                Spacer(Modifier.padding(20.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    IconButton(onClick = { viewModel.isActive = true },) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,

                                            )
                                    }
                                }
                            }

                        }
                    )
            } else {
                SearchTopBar(viewModel)
            }
        },
    ) {  paddingValue ->
            Column(Modifier.padding(paddingValue)) {
                if (viewModel.userInput == "") {
                    Text("Favorite",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            )
                    )
                    viewModel.updateFavorites()
                    LazyColumn() {
                        items(viewModel.favoriteUi.favorites) {
                            CardFlight(
                                searchItem = it.departureAirport,
                                findItem = it.destinationAirport,
                                onClick = {
                                    viewModel.addOrRemoveFavorite(it)
                                },
                                boolean = viewModel.isFavorite(
                                    it.departureAirport.iataCode,
                                    it.destinationAirport.iataCode
                                )
                            )
                        }
                    }
                } else {
                        if (!viewModel.isActive) {
                            Text(
                                "Отправление из:${viewModel.flightSearchUi.currentAirport!!.iataCode} ",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                            )
                            LazyColumn() {
                                items(viewModel.flightSearchUi.destinationAirportList) { item ->
                                    CardFlight(
                                        searchItem = viewModel.flightSearchUi.currentAirport!!,
                                        findItem = item,
                                        onClick = {
                                            viewModel.addOrRemoveFavorite(
                                                Favorite(
                                                    destinationAirport = item,
                                                    departureAirport = viewModel.flightSearchUi.currentAirport!!
                                                )
                                            )
                                        },
                                        boolean = viewModel.isFavorite(
                                            viewModel.flightSearchUi.currentAirport!!.iataCode,
                                            item.iataCode
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

@Composable
fun CardFlight(
    searchItem: Airport,
    findItem: Airport,
    onClick: () -> Unit,
    boolean: Boolean,

    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {
            Column(
                modifier = modifier
                    .weight(2f)
                    .padding(8.dp)
            ) {
                Text(
                    "ОТПРАВЛЕНИЕ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                    Text(
                        searchItem.iataCode,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        searchItem.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    "ПРИБЫТИЕ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                    Text(
                        findItem.iataCode,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        findItem.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            IconButton(onClick = onClick, modifier = Modifier.weight(0.3f)) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Add to Favorites",
                    tint = if (boolean) Color.Yellow else
                        Color.Unspecified,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    viewModel: AirportViewModel
){
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter) {
        SearchBar(
            modifier = Modifier,
            query = viewModel.userInput,
            onQueryChange = {
                viewModel.updateSearch(it)
                viewModel.getSearchListAirport(it)
                //viewModel.saveInputPreference(it)
            },
            onSearch = {
                viewModel.isActive = false
                if (viewModel.flightSearchUi.suggestedAirportList.isNotEmpty()) {
                    viewModel.updateCurrentAirport(viewModel.flightSearchUi.suggestedAirportList[0])
                    viewModel.getAllDestinationAirports()
                }
                viewModel.saveInputPreference(it)
            },
            active = viewModel.isActive,
            onActiveChange = {
                viewModel.isActive = it
                viewModel.getSearchListAirport(viewModel.userInput)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        if (viewModel.userInput == "") {
                            viewModel.isActive = false
                        } else {
                            viewModel.updateSearch("")
                        }
                    }
                )
            },
        ) {
            LazyColumn {
                items(viewModel.flightSearchUi.suggestedAirportList) {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                        .clickable {
                            viewModel.updateCurrentAirport(it)
                            viewModel.updateSearch(viewModel.flightSearchUi.currentAirport!!.iataCode)
                            viewModel.getAllDestinationAirports()
                            viewModel.isActive = false
                    }) {
                        Text(
                            it.iataCode,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            it.name,
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            fontSize = 18.sp

                        )
                    }


                }
            }
        }
    }
}

