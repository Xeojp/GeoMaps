package com.example.navigationapp

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class Converters {
    @TypeConverter
    fun fromLatLng(latLng: LatLng?): String? {
        return latLng?.let { "${it.latitude},${it.longitude}" }
    }
    
    @TypeConverter
    fun toLatLng(latLangString: String?): LatLng? {
        return latLangString?.split(",")?.let {
            if (it.size == 2) {
                LatLng(it[0].toDouble(), it[1].toDouble())
            } else {
                null
            }
        }
    }
}