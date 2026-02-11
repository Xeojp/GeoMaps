package com.example.navigationapp

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val searchViewModel: SearchViewModel = viewModel()
    val routeViewModel: RouteViewModel = viewModel()
    val app = (context.applicationContext as NavigationApplication)
    val favoritesRepository = FavoritesRepository(app.favoritesDatabase.favoriteDao())
    val favoritesViewModel: FavoritesViewModel = viewModel { 
        FavoritesViewModel(favoritesRepository) 
    }
    
    var map by remember { mutableStateOf<GoogleMap?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var showRouteInput by remember { mutableStateOf(false) }
    
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            searchViewModel.searchPlaces(searchQuery)
        } else {
            searchViewModel.clearSearch()
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for places") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        
        if (searchQuery.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(searchViewModel.places.value) { place ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            map?.moveCamera(
                                com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                                    LatLng(place.latitude, place.longitude), 15f
                                )
                            )
                            
                            map?.addMarker(
                                MarkerOptions()
                                    .position(LatLng(place.latitude, place.longitude))
                                    .title(place.name)
                            )
                            
                            searchQuery = ""
                            searchViewModel.clearSearch()
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = place.name, style = MaterialTheme.typography.bodyLarge)
                                Text(text = place.address, style = MaterialTheme.typography.bodyMedium)
                            }
                            IconButton(
                                onClick = {
                                    val favorite = Favorite(
                                        name = place.name,
                                        address = place.address,
                                        latitude = place.latitude,
                                        longitude = place.longitude
                                    )
                                    favoritesViewModel.addFavorite(favorite)
                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to favorites")
                            }
                        }
                    }
                }
            }
        }
        
        if (showRouteInput) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Route Planning", style = MaterialTheme.typography.headlineSmall)
                
                TextField(
                    value = routeViewModel.startPoint?.name ?: "",
                    onValueChange = {},
                    label = { Text("Starting point") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                TextField(
                    value = routeViewModel.endPoint?.name ?: "",
                    onValueChange = {},
                    label = { Text("Destination") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (routeViewModel.currentRoute.value != null) {
                    val route = routeViewModel.currentRoute.value!!
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(text = "Route Details", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Distance: ${route.distance}")
                            Text(text = "Duration: ${route.duration}")
                        }
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { 
                            routeViewModel.calculateRoute()
                        },
                        enabled = !routeViewModel.isCalculating && 
                                 routeViewModel.startPoint != null && 
                                 routeViewModel.endPoint != null
                    ) {
                        if (routeViewModel.isCalculating) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        } else {
                            Text("Calculate Route")
                        }
                    }
                    
                    Button(
                        onClick = { showRouteInput = false }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
        
        AndroidView(
            factory = { 
                val mapFragment = SupportMapFragment.newInstance()
                (context as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .add(mapFragment)
                    .commit()
                
                mapFragment.getMapAsync { googleMap ->
                    map = googleMap
                    
                    val defaultLocation = LatLng(55.7558, 37.6173)
                    googleMap.addMarker(MarkerOptions().position(defaultLocation).title("Default Location"))
                    googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
                    
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        googleMap.isMyLocationEnabled = true
                    }
                }
                mapFragment.view
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { 
                    isSearching = true
                },
                enabled = !isSearching
            ) {
                if (isSearching) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Search")
                }
            }
            
            Button(
                onClick = { 
                    showRouteInput = !showRouteInput
                }
            ) {
                Text("Route")
            }
            
            Button(
                onClick = { 
                    map?.animateCamera(
                        com.google.android.gms.maps.CameraUpdateFactory.zoomTo(15f)
                    )
                }
            ) {
                Text("My Location")
            }
        }
    }
}