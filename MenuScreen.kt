package com.example.projecttictactoe

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
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
    // Observerar spel- och spelaruppdateringar från GameModel
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
    // Startar en effekt för att automatiskt navigera till ett spel om användaren är inbjuden
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

    // Hämtar spelarens namn
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

    // Funktion för att uppdatera spelarens status till offline
    fun updatePlayerStatusOffline() {
        model.myPlayerId.value?.let { playerId ->
            val playerRef = firestore.collection("players").document(playerId)
            playerRef.update("status", false) // Markerar spelaren som offline
                .addOnSuccessListener {
                    Log.d("PlayerStatus", "Player status updated to offline")
                }
                .addOnFailureListener { e ->
                    Log.e("PlayerStatus", "Error updating player status: $e")
                }
        }
    }
    // Skapar tillbaka-knappen
    IconButton(
        onClick = { updatePlayerStatusOffline() // Uppdatera status
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
        // Visar en lista över spelare som är tillgängliga för utmaningar.
        items(players.entries.toList()) { entry ->
            val player = entry.value // Spelarinformation
            val documentId = entry.key // Spelarens ID

            if (documentId != model.myPlayerId.value) { // Exkludera sig själv från listan
                ListItem(
                    headlineContent = {
                        Text(text = player.name)
                    },
                    supportingContent = {
                        Text(text = "Status: ${if (player.status) "Online" else "Offline"}")
                    },
                    trailingContent = {
                        // Hantera spelknappar för utmaningar eller inbjudningar
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

    // Kolla om det redan finns ett spel med spelaren
    games.forEach { (gameId, game) ->
        if (game.player1Id == model.myPlayerId.value
            && game.player2Id == documentId
            && game.gameState == "Invite") {
            Text("Please Wait :')") // Väntar på att motståndaren ska acceptera
            hasGame = true
        } else if (game.player2Id == model.myPlayerId.value
            && game.player1Id == documentId
            && game.gameState == "Invite") {
            Button(onClick = {
                model.db.collection("games").document(gameId)
                    .update("gameState", "player1_turn")  // Starta spelet
                    .addOnSuccessListener {
                        navController.navigate("GameScreen/$gameId") // Navigera till spelet
                    }
                    .addOnFailureListener { exception ->
                        Log.e("MenuScreen", "Error updating game state: $gameId ${exception.message}")
                    }
            }) {
                Text("Accept Invite") // Knapp för att acceptera inbjudan
            }
            hasGame = true
        }
    }
    if (!hasGame) {
        // Om inget spel finns, skapa en utmaning
        Button(onClick = {
            model.db.collection("games")
                .add(
                    Game(
                        gameState = "Invite", // Spelets status
                        player1Id = model.myPlayerId.value!!, // Spelare 1
                        player2Id = documentId // Spelare 2
                    )
                )
        }) {
            Text("Challenge") // Knapp för att skicka en utmaning
        }
    }
}

@Composable
fun MusicControlButtons(context: android.content.Context) {
    // Skapa ett tillstånd för att hantera om musiken spelas
    val isPlaying = remember { mutableStateOf(true) }

    Button(onClick = {
        val intent = Intent(context, MediaService::class.java)
        intent.action = if (isPlaying.value) "PAUSE" else "RESUME"
        context.startService(intent)
        isPlaying.value = !isPlaying.value // Växlar mellan true/false
    }) {
        Text(if (isPlaying.value) "Pause Music" else "Play Music")
    }
}


@Preview
@Composable
private fun MenuScreenPreview() {
    val navController = rememberNavController()
    val model = GameModel()

    MenuScreen(navController = navController, model)
}