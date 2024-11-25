package com.example.projecttictactoe

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
//import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
//import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
//import androidx.compose.material3.value
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
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
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.com.example.projecttictactoe.GameModel
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController,
               //tictactoeList: MutableList<String>,
               model: GameModel) {
    //val userName = tictactoeList.lastOrNull() ?: "Player"
    val players by model.playerMap.asStateFlow().collectAsStateWithLifecycle()
    val games by model.gameMap.asStateFlow().collectAsStateWithLifecycle()

    LaunchedEffect(games) {
        games.forEach { (gameId, game) ->
            // TODO: Popup with accept invite?
            if ((game.player1Id == model.myPlayerId.value
                                || game.player2Id == model.myPlayerId.value)
                                && game.gameState == "player1_turn") {
                navController.navigate("GameScreen/${gameId}")
            }
        }
    }

    var playerName = "Unknown?"
    players[model.myPlayerId.value]?.let {
        playerName = it.name
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffc1aeca))
    ) {
        Text(
            text = "Game Requests $playerName",  // $userName
            color = Color(0xfff5f5f5),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            lineHeight = 1.66.em,
            style = TextStyle(
                fontSize = 38.sp,
                fontWeight = FontWeight.Light,
                shadow = Shadow(color = Color.DarkGray,
                    offset = Offset(4f, 6f),
                    blurRadius = 4f)),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp,
                    y = 147.dp)
                .requiredWidth(width = 364.dp))
        IconButton(
            onClick = { navController.navigate("HomeScreen") },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 49.dp,
                    y = 54.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = Color(0xff65558f))
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "icon",
                    modifier = Modifier
                        .fillMaxSize())
            }
        }
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 59.dp,
                    y = 320.dp)
                .requiredWidth(width = 294.dp)
                .requiredHeight(height = 574.dp)
        ) { //innerPadding ->
            //Requests(navController = navController, model = model)
            LazyColumn (modifier = Modifier) {
                items(players.entries.toList()) { entry  ->
                    val player = entry.value
                    val documentId = entry.key

                    if (documentId != model.myPlayerId.value) {
                        ListItem(
                            headlineContent =  {
                                Text("Player Name: ${player.name}")
                            },
                            supportingContent = {
                                Text("Status: ")
                            },
                            trailingContent = {
                                var hasGame = false
                                games.forEach { (gameId, game) ->
                                    if (game.player1Id == model.myPlayerId.value
                                        && game.gameState == "Invite") {
                                        Text("Please Wait :')")
                                        hasGame = true
                                    } else if (game.player2Id == model.myPlayerId.value
                                        && game.gameState == "Invite") {
                                        Button(onClick = {
                                            model.db.collection("games").document(gameId)
                                                .update("gameState", "player1_turn")
                                                .addOnSuccessListener {
                                                    navController.navigate("GameScreen/${gameId}")
                                                }
                                                .addOnFailureListener {
                                                    Log.e("merry christmas", "error updating game: $gameId")
                                                }
                                        }) {
                                            Text("Accept invite")
                                        }
                                        hasGame = true
                                    }
                                }
                                if (!hasGame) {
                                    Button(onClick = {
                                        model.db.collection("games")
                                            .add(Game(gameState = "Invite",
                                                player1Id = model.myPlayerId.value!!,
                                                player2Id = documentId
                                            ))
                                            .addOnSuccessListener {documentRef ->
                                                //TODO
                                            }
                                    }) {
                                        Text("Challenge")
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
/*
@Composable
fun Requests(navController: NavController,
             modifier: Modifier = Modifier,
             model: GameModel) {
    Column(
        modifier = Modifier
            .requiredWidth(width = 294.dp)
            .requiredHeight(height = 389.dp)
            .background(color = Color(0xfffef7ff))
    ) {
        /*repeat(8) { //List connected to the fire base
            Condition1LineLeadingMonogramTrailingCheckBoxShowOverlineFalseSho(navController = navController)
        }*/


    }
}

 */
/*
@Composable
fun Condition1LineLeadingMonogramTrailingCheckBoxShowOverlineFalseSho(
    navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 48.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 294.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 48.dp)
                    .padding(horizontal = 16.dp,
                        vertical = 4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    BuildingBlocksMonogram()
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f)
                ) {
                    Text(
                        text = "Username",
                        color = Color(0xff1d1b20),
                        lineHeight = 1.5.em,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(align = Alignment.CenterVertically))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TypeSelectedStateEnabled(navController = navController)
                }
            }
        }
        BuildingBlocksstatelayer1Enabled()
    }
}

@Composable
fun BuildingBlocksMonogram(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredSize(size = 40.dp)
            .clip(shape = RoundedCornerShape(100.dp))
            .background(color = Color(0xffeaddff))
    ) {
        Text(
            text = "A",
            color = Color(0xff4f378a),
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.15.sp),
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .offset(x = 0.dp,
                    y = 0.dp)
                .requiredSize(size = 40.dp)
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Composable
fun TypeSelectedStateEnabled(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(end = 4.dp, top = 4.dp, bottom = 4.dp)
    ) {
        // Wrapping the Checkbox with an IconButton that triggers navigation
        IconButton(
            onClick = {
                // Navigate to the game screen when the checkbox is clicked
                navController.navigate("GameScreen")
            },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(100.dp))
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(all = 11.dp)
                ) {
                    // Checkbox is displayed as checked but not interactable
                    Checkbox(
                        checked = true,  // Always display as checked
                        onCheckedChange = null  // Disable interaction
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.check_small),
                    contentDescription = "check_small",
                    tint = Color.Transparent,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .offset(x = 0.dp, y = 0.dp)
                )
            }
        }
    }
}

@Composable
fun BuildingBlocksstatelayer1Enabled(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize())
}
*/
@Preview
@Composable
private fun MenuScreenPreview() {
    val navController = rememberNavController()
    //val tictactoeList = remember { mutableStateListOf("SampleUser") }
    val model = GameModel()

    MenuScreen(navController = navController,
        //tictactoeList = tictactoeList,
        model)
}