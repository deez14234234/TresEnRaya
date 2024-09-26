package com.example.tic_tac_toe.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tic_tac_toe.data.local.Game
import com.example.tic_tac_toe.data.local.GameDao
import com.example.tic_tac_toe.data.local.GameDatabase
import com.example.tic_tac_toe.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val gameDao: GameDao = GameDatabase.getDatabase(application).gameDao()
    private val repository: GameRepository = GameRepository(gameDao)

    private val _boardState = MutableStateFlow(List(9) { 0 })
    val boardState: StateFlow<List<Int>> = _boardState

    private val _currentPlayer = MutableStateFlow(1)
    val currentPlayer: StateFlow<Int> = _currentPlayer

    private val _winner = MutableStateFlow(0)
    val winner: StateFlow<Int> = _winner

    private val _winningCombination = MutableStateFlow<List<Int>>(emptyList())
    val winningCombination: StateFlow<List<Int>> = _winningCombination

    private val _savedGames = MutableStateFlow<List<Game>>(emptyList())
    val savedGames: StateFlow<List<Game>> = _savedGames

    init {
        loadSavedGames()
    }

    fun makeMove(index: Int) {
        if (_boardState.value[index] == 0 && _winner.value == 0) {
            _boardState.value = _boardState.value.toMutableList().apply {
                this[index] = _currentPlayer.value
            }
            checkForWinner()
            _currentPlayer.value = if (_currentPlayer.value == 1) 2 else 1
        }
    }

    private fun checkForWinner() {
        val board = _boardState.value
        val winningCombinations = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (combination in winningCombinations) {
            if (board[combination[0]] != 0 &&
                board[combination[0]] == board[combination[1]] &&
                board[combination[1]] == board[combination[2]]
            ) {
                _winner.value = board[combination[0]]
                _winningCombination.value = combination // Guarda la combinación ganadora
                return
            }
        }
        if (board.none { it == 0 }) {
            _winner.value = -1 // Empate
        }
    }

    fun getWinningCombination(boardState: List<Int>, player: Int): List<Int>? {
        val winningCombinations = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (combination in winningCombinations) {
            if (boardState[combination[0]] == player &&
                boardState[combination[1]] == player &&
                boardState[combination[2]] == player
            ) {
                return combination
            }
        }
        return null
    }

    fun resetGame() {
        _boardState.value = List(9) { 0 }
        _currentPlayer.value = 1
        _winner.value = 0
        _winningCombination.value = emptyList() // Reiniciar la combinación ganadora
    }

    private fun loadSavedGames() {
        viewModelScope.launch {
            try {
                repository.allGames.collect { games ->
                    _savedGames.value = games
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error loading saved games: ${e.message}")
            }
        }
    }

    fun saveCurrentGame(player1Name: String, player2Name: String) {
        viewModelScope.launch {
            try {
                val currentGame = Game(
                    player1 = player1Name,
                    player2 = player2Name,
                    boardState = _boardState.value.joinToString(","),
                    winner = _winner.value
                )
                repository.insertGame(currentGame)
                Log.d("GameViewModel", "Game saved with players: $player1Name vs $player2Name")
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error saving game: ${e.message}")
            }
        }
    }
}
