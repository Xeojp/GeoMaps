package com.example.navigationapp

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null
        
        fun getDatabase(context: Context): FavoritesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "favorites_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}