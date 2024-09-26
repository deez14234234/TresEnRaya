package com.example.tic_tac_toe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val player1: String, // Ahora se guarda el nombre personalizado
    val player2: String, // Ahora se guarda el nombre personalizado
    val boardState: String,
    val winner: Int // 0 = sin ganador, 1 = jugador 1, 2 = jugador 2, -1 = empate
)