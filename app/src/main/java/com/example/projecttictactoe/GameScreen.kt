package com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, model: GameModel, gameId: String) {
    val players by model.playerMap.asStateFlow().collectAsStateWithLifecycle()
    val games by model.gameMap.asStateFlow().collectAsStateWithLifecycle()
    val currentGame = games[gameId]

    // local copies of gameState
    var localGameState by remember { mutableStateOf(currentGame?.gameState ?: "loading") }
    var localGameBoard by remember { mutableStateOf(currentGame?.gameBoard ?: List(9) { 0 }) }

    LaunchedEffect(currentGame) {
        currentGame?.let {
            localGameState = it.gameState
            localGameBoard = it.gameBoard
        }
    }

    if (currentGame != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffc1aeca))
        ) {
            Title(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                model = model,
                gameId = gameId
            )

            Spacer(modifier = Modifier.height(16.dp))

            TicTacToeBoard(
                boardState = localGameBoard,
                onBoxClick = { index ->
                    // prevent click if game-over
                    if (localGameState.endsWith("_won") || localGameState == "draw") {
                        return@TicTacToeBoard
                    }

                    // Identify player's turn
                    val currentPlayerId = if (localGameState == "player1_turn") {
                        currentGame?.player1Id
                    } else if (localGameState == "player2_turn") {
                        currentGame?.player2Id
                    } else null

                    // if local player's turn
                    if (model.myPlayerId.value != currentPlayerId) {
                        return@TicTacToeBoard
                    }

                    // if box is empty and make move
                    if (localGameBoard[index] == 0) {
                        val currentPlayer = if (localGameState == "player1_turn") 1 else 2
                        localGameBoard = localGameBoard.toMutableList().apply { set(index, currentPlayer) }

                        // control winner or draw
                        val winner = checkForWinner(localGameBoard)
                        localGameState = when {
                            winner != null -> if (winner == 1) "player1_won" else "player2_won"
                            localGameBoard.none { it == 0 } -> "draw"
                            else -> if (currentPlayer == 1) "player2_turn" else "player1_turn"
                        }

                        // Updater Firestore
                        model.db.collection("games").document(gameId)
                            .update(
                                mapOf(
                                    "gameBoard" to localGameBoard,
                                    "gameState" to localGameState
                                )
                            )
                    }
                },
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // player turn and winner
            when {
                localGameState.endsWith("_won") -> {
                    val winnerName = if (localGameState.startsWith("player1")) {
                        players[currentGame.player1Id]?.name ?: "Player 1"
                    } else {
                        players[currentGame.player2Id]?.name ?: "Player 2"
                    }
                    Text(
                        text = "$winnerName won the game!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

                localGameState == "draw" -> {
                    Text(
                        text = "It's a draw!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

                else -> {
                    val currentTurnPlayerName = if (localGameState == "player1_turn") {
                        players[currentGame.player1Id]?.name ?: "Player 1"
                    } else {
                        players[currentGame.player2Id]?.name ?: "Player 2"
                    }
                    Text(
                        text = "$currentTurnPlayerName's turn",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier, model: GameModel, gameId: String) {
    val games by model.gameMap.asStateFlow().collectAsStateWithLifecycle()
    val players by model.playerMap.asStateFlow().collectAsStateWithLifecycle()

    val currentGame = games[gameId]
    val myPlayerId = model.myPlayerId.value

    val myName = players[myPlayerId]?.name ?: "Me"
    val opName = if (myPlayerId == currentGame?.player1Id) {
        players[currentGame!!.player2Id]!!.name
    } else {
        players[currentGame?.player1Id]?.name ?: "Opponent"
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Tic Tac Toe",
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 2.5.em,
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 8f),
                    blurRadius = 4f
                )
            ),
            modifier = Modifier.fillMaxWidth()
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

@Composable
fun TicTacToeBoard(
    boardState: List<Int>,
    onBoxClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .requiredSize(300.dp)
            .background(Color.White.copy(alpha = 0.6f))
            .border(border = BorderStroke(2.dp, Color.Black))
    ) {
        items(9) { index ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(BorderStroke(1.dp, Color.Black))
                    .clickable { onBoxClick(index) }
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

    for (combination in winningCombinations) {
        val (a, b, c) = combination
        if (boardState[a] != 0 &&
            boardState[a] == boardState[b] &&
            boardState[a] == boardState[c]
            ) {
            return boardState[a]
        }
    }
    return null
}

@Composable
fun Frame18(navController: NavController,
            modifier: Modifier = Modifier,
            onDismiss: () -> Unit,
            winnerId: String) {
    Surface(
        color = Color(0xffe7e0ec),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 320.dp)
                .requiredHeight(height = 627.dp)
        ) {
            val displayText = if (winnerId == "Draw") "It was a Draw" else "Player $winnerId won!"
            Text(
                text = displayText,
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                lineHeight = 1.06.em,
                style = TextStyle(
                    fontSize = 32.sp,
                    letterSpacing = 0.1.sp
                ),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 70.dp, y = 120.dp)
                    .requiredWidth(width = 197.dp)
                    .requiredHeight(height = 119.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            Button(
                onClick = { onDismiss(); navController.navigate("MenuScreen")},
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff65558f)),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 110.dp, y = 253.dp)
                    .requiredHeight(height = 40.dp)
                    .requiredWidth(width = 120.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .requiredHeight(height = 40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Go to Menu",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 1.43.em,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
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