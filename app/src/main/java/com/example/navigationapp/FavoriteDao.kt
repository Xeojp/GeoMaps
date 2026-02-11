package com.example.navigationapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<Favorite>>
    
    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getFavoriteById(id: Long): Favorite?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)
    
    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavorite(id: Long)
    
    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()
}