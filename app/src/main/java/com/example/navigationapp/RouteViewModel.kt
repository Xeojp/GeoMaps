package com.example.navigationapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RoutePoint(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

data class Route(
    val startPoint: RoutePoint,
    val endPoint: RoutePoint,
    val distance: String,
    val duration: String,
    val points: List<LatLng>
)

class RouteViewModel : ViewModel() {
    private val _currentRoute = MutableStateFlow<Route?>(null)
    val currentRoute: StateFlow<Route?> = _currentRoute
    
    private val _isCalculating = mutableStateOf(false)
    val isCalculating: Boolean by _isCalculating
    
    private val _startPoint = mutableStateOf<RoutePoint?>(null)
    val startPoint: RoutePoint? by _startPoint
    
    private val _endPoint = mutableStateOf<RoutePoint?>(null)
    val endPoint: RoutePoint? by _endPoint
    
    fun setStartPoint(point: RoutePoint) {
        _startPoint.value = point
    }
    
    fun setEndPoint(point: RoutePoint) {
        _endPoint.value = point
    }
    
    fun calculateRoute() {
        _isCalculating.value = true
        
        kotlinx.coroutines.delay(1500)
        
        val start = _startPoint.value ?: return
        val end = _endPoint.value ?: return
        
        val mockPoints = listOf(
            LatLng(start.latitude, start.longitude),
            LatLng(start.latitude + 0.01, start.longitude + 0.01),
            LatLng(end.latitude - 0.01, end.longitude - 0.01),
            LatLng(end.latitude, end.longitude)
        )
        
        val route = Route(
            startPoint = start,
            endPoint = end,
            distance = "5.2 km",
            duration = "25 мин",
            points = mockPoints
        )
        
        _currentRoute.value = route
        _isCalculating.value = false
    }
    
    fun clearRoute() {
        _currentRoute.value = null
        _startPoint.value = null
        _endPoint.value = null
    }
}