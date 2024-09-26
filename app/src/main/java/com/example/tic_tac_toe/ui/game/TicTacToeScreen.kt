package com.example.tic_tac_toe.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tic_tac_toe.viewmodel.GameViewModel


@Composable
fun TicTacToeScreen(viewModel: GameViewModel = viewModel()) {
    var player1Name by remember { mutableStateOf("Player 1") }
    var player2Name by remember { mutableStateOf("Player 2") }

    val boardState by viewModel.boardState.collectAsState()
    val currentPlayer by viewModel.currentPlayer.collectAsState()
    val winner by viewModel.winner.collectAsState()
    val winningCombination by viewModel.winningCombination.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Juego de 3 en Raya",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = player1Name,
            onValueChange = { player1Name = it },
            label = { Text("Nombre del Jugador 1") },
            modifier = Modifier.padding(8.dp)
        )

        TextField(
            value = player2Name,
            onValueChange = { player2Name = it },
            label = { Text("Nombre del Jugador 2") },
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = "Le toca a: ${if (currentPlayer == 1) player1Name else player2Name}",
            style = MaterialTheme.typography.titleMedium
        )

        // Tablero del juego
        Column {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        val index = i * 3 + j
                        val isWinningButton = winningCombination.contains(index)
                        Button(
                            onClick = { viewModel.makeMove(index) },
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isWinningButton && winner != 0) Color.Green else Color.LightGray
                            )
                        ) {
                            val buttonText = when (boardState[index]) {
                                1 -> "X"
                                2 -> "O"
                                else -> ""
                            }
                            Text(
                                text = buttonText,
                                fontSize = 70.sp,
                                color = when (buttonText) {
                                    "X" -> Color.Red
                                    "O" -> Color.Blue
                                    else -> Color.Transparent
                                },
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // Mostrar el ganador o empate
        if (winner != 0) {
            Text(
                text = when (winner) {
                    1 -> "$player1Name Gana!"
                    2 -> "$player2Name Gana!"
                    -1 -> "¡Es un empate!"
                    else -> ""
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            viewModel.saveCurrentGame(player1Name, player2Name)
        }

        // Botón para reiniciar el juego
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { viewModel.resetGame() },
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp) // Tamaño fijo para el botón
            ) {
                Text(text = "Jugar de Nuevo")
            }
        }
    }
}


