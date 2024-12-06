package com.example.projecttictactoe

import android.annotation.SuppressLint
//import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.Models.Game
import com.example.projecttictactoe.Models.Player
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.forEach

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, model: GameModel) {
    // Observes game and player updates from the model
    val games by model.gameMap.collectAsState()
    val players by model.playerMap.collectAsState()
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? = remember { null }

    LaunchedEffect(Unit) {
        mediaPlayer = MediaPlayer.create(context, R.raw.menu_music)
        mediaPlayer?.setOnCompletionListener { it.release() }
        mediaPlayer?.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
    // Starts an effect to automatically navigate to a game if the user is invited
    LaunchedEffect(games) {
        games.forEach { (gameId, game) ->
            if ((game.player1Id == model.myPlayerId.value
                        || game.player2Id == model.myPlayerId.value)
                && game.gameState == "player1_turn"
            ) {
                navController.navigate("GameScreen/${gameId}")
            }
        }
    }

    // Retrieves the player's name
    var playerName = "Unknown?"
    players[model.myPlayerId.value]?.let {
        playerName = it.name
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xffc1aeca),
        topBar = {
            Back_Icon_Home(model = model, navController = navController)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Header(playerName = playerName)
                Spacer(modifier = Modifier.height(16.dp))
                GameRequestList(
                    players = players,
                    games = games,
                    model = model,
                    navController = navController
                )
                // MusicControlButtons(context = context)
            }
        }
    )
}


@Composable
fun Back_Icon_Home (navController: NavController, model: GameModel) {
    val firestore = FirebaseFirestore.getInstance()

    // Function to update the player's status to offline
    fun updatePlayerStatusOffline() {
        model.myPlayerId.value?.let { playerId ->
            val playerRef = firestore.collection("players").document(playerId)
            playerRef.update("status", false) // Marks the player as offline
                .addOnSuccessListener {
                    Log.d("PlayerStatus", "Player status updated to offline")
                }
                .addOnFailureListener { e ->
                    Log.e("PlayerStatus", "Error updating player status: $e")
                }
        }
    }
    // Creates the back button
    IconButton(
        onClick = { updatePlayerStatusOffline() // Update status
            navController.navigate("HomeScreen") },
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(50.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(color = Color(0xff65558f))
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Back icon",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun Header (playerName: String) {
    Text(
        text = "Game Requests \n$playerName",
        color = Color(0xfff5f5f5),
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        lineHeight = 1.66.em,
        style = TextStyle(
            fontSize = 38.sp,
            fontWeight = FontWeight.Light,
            shadow = Shadow(color = Color.DarkGray,
                offset = Offset(4f, 6f),
                blurRadius = 4f
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp, bottom = 16.dp)
    )
}

@Composable
fun GameRequestList(
    players: Map<String, Player>,
    games: Map<String, Game>,
    model: GameModel,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp) // Adjust based on available space
    ) {
        // Iterates over players
        items(players.entries.toList()) { entry ->
            val player = entry.value // Player information
            val documentId = entry.key // Players ID

            if (documentId != model.myPlayerId.value) { // Exclude self from the list
                ListItem(
                    headlineContent = {
                        Text(text = player.name)
                    },
                    supportingContent = {
                        Text(text = "Status: ${if (player.status) "Online" else "Offline"}")
                    },
                    trailingContent = {
                        // Handle game buttons for challenges or invitations
                        ManageGameButtons(
                            games = games,
                            model = model,
                            navController = navController,
                            documentId = documentId
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ManageGameButtons(
    games: Map<String, Game>,
    model: GameModel,
    navController: NavController,
    documentId: String
) {
    var hasGame = false

    // Check if there is already a game with the player
    games.forEach { (gameId, game) ->
        if (game.player1Id == model.myPlayerId.value
            && game.player2Id == documentId
            && game.gameState == "Invite") {
            Text("Please Wait :')") // Waiting for the opponent to accept
            hasGame = true
        } else if (game.player2Id == model.myPlayerId.value
            && game.player1Id == documentId
            && game.gameState == "Invite") {
            Button(onClick = {
                model.db.collection("games").document(gameId)
                    .update("gameState", "player1_turn")  // Start game
                    .addOnSuccessListener {
                        navController.navigate("GameScreen/$gameId") // Navigate to game
                    }
                    .addOnFailureListener { exception ->
                        Log.e("MenuScreen", "Error updating game state: $gameId ${exception.message}")
                    }
            }) {
                Text("Accept Invite") // Button to accept invitation
            }
            hasGame = true
        }
    }
    if (!hasGame) {
        // If no game exists, create a challenge
        Button(onClick = {
            model.db.collection("games")
                .add(
                    Game(
                        gameState = "Invite", // Game status
                        player1Id = model.myPlayerId.value!!, // Player 1
                        player2Id = documentId // Player 2
                    )
                )
        }) {
            Text("Challenge") // Button to send a challenge
        }
    }
}

/*
@Composable
fun MusicControlButtons(context: android.content.Context) {
    // Creates a state to manage whether the music is playing
    val isPlaying = remember { mutableStateOf(true) }

    Button(onClick = {
        val intent = Intent(context, MediaService::class.java)
        intent.action = if (isPlaying.value) "PAUSE" else "RESUME"
        context.startService(intent)
        isPlaying.value = !isPlaying.value // Toggles between true/false
    }) {
        Text(if (isPlaying.value) "Pause Music" else "Play Music")
    }
}
*/

@Preview
@Composable
private fun MenuScreenPreview() {
    val navController = rememberNavController()
    val model = GameModel()

    MenuScreen(navController = navController, model)
}