package com.example.navigationapp

import android.app.Application
import androidx.room.Room

class NavigationApplication : Application() {
    lateinit var favoritesDatabase: FavoritesDatabase
    
    override fun onCreate() {
        super.onCreate()
        
        favoritesDatabase = Room.databaseBuilder(
            applicationContext,
            FavoritesDatabase::class.java,
            "favorites_database"
        ).build()
    }
}