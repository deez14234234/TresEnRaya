package com.example.tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tic_tac_toe.ui.game.TicTacToeScreen
import com.example.tic_tac_toe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeScreen(viewModel = viewModel())
        }
    }
}