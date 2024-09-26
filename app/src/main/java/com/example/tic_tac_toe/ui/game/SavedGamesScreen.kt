package com.example.tic_tac_toe.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tic_tac_toe.data.local.Game
import com.example.tic_tac_toe.viewmodel.GameViewModel

@Composable
fun SavedGamesScreen() {
    val viewModel: GameViewModel = viewModel()
    val savedGames = viewModel.savedGames.collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Saved Games", style = MaterialTheme.typography.headlineMedium)


        if (savedGames.isEmpty()) {
            Text(text = "No saved games yet.", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            for (game in savedGames) {
                GameItem(game = game)
            }
        }
    }
}

@Composable
fun GameItem(game: Game) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Game ID: ${game.id}") // Asegúrate de que 'id' esté en tu clase Game
        Text(text = "Winner: ${if (game.winner == 0) "None" else game.winner}") // Asegúrate de que 'winner' esté en tu clase Game
        // Puedes agregar más información del juego aquí
    }
}
