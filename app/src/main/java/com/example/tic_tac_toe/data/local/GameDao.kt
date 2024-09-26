package com.example.tic_tac_toe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM games")
    fun getAllGames(): Flow<List<Game>>  // Devolver la lista de juegos como Flow
}
