package com.example.projecttictactoe

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.com.example.projecttictactoe.GameModel
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, model: GameModel) {
    val players by model.playerMap.asStateFlow().collectAsStateWithLifecycle()
    val games by model.gameMap.asStateFlow().collectAsStateWithLifecycle()

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

    var playerName = "Unknown?"
    players[model.myPlayerId.value]?.let {
        playerName = it.name
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xffc1aeca),
        topBar = {
            TopBar(navController = navController)
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
            }
        }
    )
}

@Composable
fun TopBar (navController: NavController) {
    IconButton(
        onClick = { navController.navigate("HomeScreen") },
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
        items(players.entries.toList()) { entry ->
            val player = entry.value
            val documentId = entry.key

            if (documentId != model.myPlayerId.value) {
                ListItem(
                    headlineContent = {
                        Text(text = "Player Name: ${player.name}")
                    },
                    supportingContent = {
                        Text(text = "Status:")
                    },
                    trailingContent = {
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
    games.forEach { (gameId, game) ->
        if (game.player1Id == model.myPlayerId.value
            && game.player2Id == documentId
            && game.gameState == "Invite") {
            Text("Please Wait :')")
            hasGame = true
        } else if (game.player2Id == model.myPlayerId.value
            && game.player1Id == documentId
            && game.gameState == "Invite") {
            Button(onClick = {
                model.db.collection("games").document(gameId)
                    .update("gameState", "player1_turn")
                    .addOnSuccessListener {
                        navController.navigate("GameScreen/$gameId")
                    }
                    .addOnFailureListener {
                        Log.e("Error", "Error updating game: $gameId")
                    }
            }) {
                Text("Accept Invite")
            }
            hasGame = true
        }
    }
    if (!hasGame) {
        Button(onClick = {
            model.db.collection("games")
                .add(
                    Game(
                        gameState = "Invite",
                        player1Id = model.myPlayerId.value!!,
                        player2Id = documentId
                    )
                )
        }) {
            Text("Challenge")
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    val navController = rememberNavController()
    val model = GameModel()

    MenuScreen(navController = navController, model)
}