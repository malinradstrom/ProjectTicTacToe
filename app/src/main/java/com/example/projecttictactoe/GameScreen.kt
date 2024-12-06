package com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, model: GameModel, gameId: String) {
    val games by model.gameMap.collectAsState()
    val players by model.playerMap.collectAsState()

    val currentGame = games[gameId]

    var localGameBoard by remember { mutableStateOf(currentGame?.gameBoard ?: List(9) { 0 }) }
    var localGameState by remember { mutableStateOf(currentGame?.gameState ?: "loading") }

    val myPlayerId = model.myPlayerId.value

    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? = remember { null }

    // Update the local state when Firestore-data changes
    LaunchedEffect(currentGame) {
        currentGame?.let {
            localGameState = it.gameState
            localGameBoard = it.gameBoard
        }
    }

    // Start background music
    LaunchedEffect(Unit) {
        mediaPlayer = MediaPlayer.create(context, R.raw.jingle_bells).apply {
            isLooping = true
            start()
        }
    }

    // Stop music and release resources when the screen moves to next
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffc1aeca))
    ) {
        // Back-button overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Back_Icon_Menu(
                navController = navController,
                stopMusic = {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                },
                modifier = Modifier
                    .align(Alignment.TopStart) // Place in the top-left corner
            )
        }

        // Main content (game UI)
        if (currentGame != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(
                    modifier = Modifier.padding(top = 16.dp),
                    model = model,
                    gameId = gameId
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tic Tac Toe board
                TicTacToeBoard(
                    boardState = localGameBoard,
                    gameState = localGameState,
                    onBoxClick = { index ->
                        if (!localGameState.endsWith("_won") && localGameState != "draw") {
                            if (localGameState.startsWith("player") &&
                                myPlayerId == (if (localGameState == "player1_turn") currentGame.player1Id else currentGame.player2Id)
                            ) {
                                val newBoard = localGameBoard.toMutableList().apply {
                                    set(index, if (localGameState == "player1_turn") 1 else 2)
                                }
                                val newState = checkForWinner(newBoard)?.let { winner ->
                                    if (winner == 1) "player1_won" else "player2_won"
                                } ?: if (newBoard.none { it == 0 }) "draw" else if (localGameState == "player1_turn") "player2_turn" else "player1_turn"
                                localGameBoard = newBoard
                                localGameState = newState
                                model.updateGame(gameId, newBoard, newState)
                            }
                        }
                    }
                )

                when {
                    localGameState.endsWith("_won") -> Text(
                        text = "${if (localGameState.startsWith("player1")) players[currentGame.player1Id]?.name else players[currentGame.player2Id]?.name} won!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    localGameState == "draw" -> Text("It's a draw!")
                    else -> Text("${if (localGameState == "player1_turn") players[currentGame.player1Id]?.name else players[currentGame.player2Id]?.name}'s turn")
                }
            }
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier, model: GameModel, gameId: String) {
    // Fetch data for games and players from the model
    val games by model.gameMap.collectAsState()
    val players by model.playerMap.collectAsState()

    // Retrieve the current game based on gameId
    val currentGame = games[gameId]
    val myPlayerId = model.myPlayerId.value

    // Fetch usernames for the player and opponent
    val myName = players[myPlayerId]?.name ?: "Me"
    val opName = if (myPlayerId == currentGame?.player1Id) {
        players[currentGame!!.player2Id]!!.name
    } else {
        players[currentGame?.player1Id]?.name ?: "Opponent"
    }

    // Display title and player information
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Tic Tac Toe",
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 2.5.em, // Line spacing 2.5 em.
            style = TextStyle( // Defines the text's appearance with size, bold style, and a shadow.
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 8f),
                    blurRadius = 4f
                )
            ),
            modifier = Modifier.fillMaxWidth() // Makes the text take up the full width of the layout.
        )
        Text(
            text = "$myName vs $opName",
            color = Color(0xff1e1e1e),
            textAlign = TextAlign.Center,
            lineHeight = 5.em,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}
// This function defines the actual game board and how it is displayed to the user:
@Composable
fun TicTacToeBoard(
    boardState: List<Int>, // Current state of the game board (0 = empty, 1 = X, 2 = O)
    gameState: String, // Game status
    onBoxClick: (Int) -> Unit, // Callback for click events
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Specifies that the grid should have 3 columns
        modifier = modifier // Sets size, background color, and a border around the board
            .requiredSize(300.dp)
            .background(Color.White.copy(alpha = 0.6f))
            .border(border = BorderStroke(2.dp, Color.Black))
    ) {
        items(9) { index ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f) // Quadrant squares
                    .border(BorderStroke(1.dp, Color.Black))
                    // Only clickable if the square is empty and the game isn't over
                    .clickable(enabled = gameState.startsWith("player") && boardState[index] == 0) {
                        onBoxClick(index)
                    }
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (boardState[index]) {
                        1 -> "X"
                        2 -> "O"
                        else -> ""
                    },
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun checkForWinner(boardState: List<Int>): Int? {
    val winningCombinations = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
        listOf(0, 4, 8), listOf(2, 4, 6)
    )

    // Control each winning combination
    for (combination in winningCombinations) {
        val (a, b, c) = combination
        if (boardState[a] != 0 &&
            boardState[a] == boardState[b] &&
            boardState[a] == boardState[c]
        ) {
            return boardState[a] // Return winner (1 or 2)
        }
    }
    return null // Return null if no one won
}

@Composable
fun Back_Icon_Menu(
    navController: NavController,
    stopMusic: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {
            stopMusic()
            navController.navigate("MenuScreen")
        },
        modifier = modifier
            .padding(16.dp)
            .size(50.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(color = Color(0xff65558f))
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Back to Menu",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun GameScreenPreview() {
    val navController = rememberNavController()
    val model = GameModel()
    val gameId = ("gameId")

    GameScreen(navController = navController, model, gameId)
}