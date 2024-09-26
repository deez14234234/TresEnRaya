package com.example.tic_tac_toe.data.repository

import com.example.tic_tac_toe.data.local.Game
import com.example.tic_tac_toe.data.local.GameDao
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {
    val allGames: Flow<List<Game>> = gameDao.getAllGames()

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }
}
