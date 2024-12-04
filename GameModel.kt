package com.example.projecttictactoe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projecttictactoe.Models.Player
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.projecttictactoe.Models.Game
import kotlinx.coroutines.flow.StateFlow
import android.util.Log

class GameModel : ViewModel() {
    val db = Firebase.firestore // Firestore-instans

    var myPlayerId = mutableStateOf<String?>(null) // Spelarens ID
    val username = MutableStateFlow<String?>(null) // Spelarens användarnamn

    private val _playerMap = MutableStateFlow<Map<String, Player>>(emptyMap()) // Spelarkarta
    val playerMap: StateFlow<Map<String, Player>> = _playerMap

    private val _gameMap = MutableStateFlow<Map<String, Game>>(emptyMap()) // Spelkarta
    val gameMap: StateFlow<Map<String, Game>> = _gameMap

    /* Observerar laddningsstatus (endast internt)
    private val _isLoading = MutableStateFlow(false)
    */

    init {
        startGameListener()
        startPlayerListener()
    }

    fun startGameListener() {
        db.collection("games").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("GameModel", "Error fetching games: ${exception.localizedMessage}")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _gameMap.value = snapshot.documents.mapNotNull { document ->
                    val game = document.toObject(Game::class.java)
                    game?.let { document.id to it }
                }.toMap()
            }
        }
    }


    fun startPlayerListener() {
        db.collection("players").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("GameModel", "Error fetching players: ${exception.localizedMessage}")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _playerMap.value = snapshot.documents.mapNotNull { document ->
                    val player = document.toObject(Player::class.java)
                    player?.let { document.id to it }
                }.toMap()
            }
        }
    }

    fun createPlayer(inputName: String, onSuccess: (String) -> Unit) {
        val newPlayer = Player(name = inputName)
        db.collection("players").add(newPlayer).addOnSuccessListener { documentRef ->
            val newPlayerId = documentRef.id
            myPlayerId.value = newPlayerId
            username.value = inputName
            onSuccess(newPlayerId)
        }
    }

    fun updateGame(gameId: String, newBoard: List<Int>, newState: String) {
        db.collection("games").document(gameId)
            .update(mapOf("gameBoard" to newBoard, "gameState" to newState))
            .addOnSuccessListener {
                Log.d("GameModel", "Game state updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("GameModel", "Failed to update game state: ${exception.message}")
            }
    }
}
/*
*  addSnapshotListner är både spel och spelare alltid synksroniserade med Firestore.
*  StateFlow och mutableStateOf gör att UI-komponenter uppdateras automatiskt när data ändras.
* */