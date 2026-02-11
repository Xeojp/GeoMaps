package com.example.navigationapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(val repository: FavoritesRepository) : ViewModel() {
    
    private val _favorites = mutableStateOf<List<Favorite>>(emptyList())
    val favorites: List<Favorite> by _favorites
    
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean by _isLoading
    
    init {
        loadFavorites()
    }
    
    private fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            _isLoading.value = false
        }
    }
    
    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }
    
    fun removeFavorite(favoriteId: Long) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteId)
        }
    }
    
    fun isFavorite(favoriteId: Long): Boolean {
        return _favorites.value.any { it.id == favoriteId }
    }
}

class FavoritesRepository(val favoriteDao: FavoriteDao) {
    
    fun getAllFavorites() = favoriteDao.getAllFavorites()
    
    suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }
    
    suspend fun deleteFavorite(favoriteId: Long) {
        favoriteDao.deleteFavorite(favoriteId)
    }
}