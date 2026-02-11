package com.example.navigationapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Place(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)

class SearchViewModel : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places
    
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean by _isLoading
    
    private val _searchQuery = mutableStateOf("")
    val searchQuery: String by _searchQuery
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun searchPlaces(query: String) {
        _isLoading.value = true
        
        kotlinx.coroutines.delay(1000)
        
        val mockResults = listOf(
            Place("1", "Moscow Kremlin", "Red Square, Moscow", 55.7512, 37.6184),
            Place("2", "St. Basil's Cathedral", "Red Square, Moscow", 55.7539, 37.6208),
            Place("3", "Gorky Park", "Gorky Park, Moscow", 55.7725, 37.6222),
            Place("4", "Tretyakov Gallery", "119, Tverskaya St, Moscow", 55.7772, 37.6184),
            Place("5", "Bolshoi Theatre", "14, Bolshaya Dmitrovka St, Moscow", 55.7772, 37.6184)
        ).filter { place ->
            place.name.contains(query, ignoreCase = true) || 
            place.address.contains(query, ignoreCase = true)
        }
        
        _places.value = mockResults
        _isLoading.value = false
    }
    
    fun clearSearch() {
        _places.value = emptyList()
        _searchQuery.value = ""
    }
}