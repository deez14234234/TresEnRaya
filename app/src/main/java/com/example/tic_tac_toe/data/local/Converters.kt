package com.example.tic_tac_toe.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromBoardState(value: List<Int>?): String {
        return value?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toBoardState(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }
}
