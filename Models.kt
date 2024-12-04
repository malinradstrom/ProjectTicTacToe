package com.example.projecttictactoe

object Models {
    data class Player(
        val id: String = "",
        val name: String = "",
        val status: Boolean = true //assuming true is online
    )
    
    data class Game(
        val gameId: String = "",
        val gameBoard: List<Int> = List(9) { 0 }, // 0: empty, 1: player1's move, 2: player2's move
        val gameState: String = "invite", // GameState = GameState.INVITE,
        val player1Id: String = "",
        val player2Id: String = ""
    )
}

