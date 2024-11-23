package com.example.projecttictactoe

//import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
//import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.com.example.projecttictactoe.GameModel
import com.example.projecttictactoe.ui.theme.ProjectTicTacToeTheme

/*
class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTicTacToeTheme {
                val navController = rememberNavController()
                val tictactoeList = remember { mutableStateListOf<String>() }

                TicTacToeApp(navController, tictactoeList)
            }
        }
    }
}

@Composable
fun TicTacToeApp(navController: NavHostController, tictactoeList: MutableList<String>) {
    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen(navController, tictactoeList) }
        composable("MenuScreen") { MenuScreen(navController, tictactoeList) }
        composable("GameScreen") { GameScreen(navController, tictactoeList) }
    }
}
*/

data class Player(
    var name: String = ""
)

data class Game(
    var gameBoard: List<Int> = List(9) { 0 }, // 0: empty, 1: player1's move, 2: player2's move
    var gameState: String = "invite", // Possible values: "invite", "player1_turn", "player2_turn" "player1_won", "player2_won", "draw"
    var player1Id: String = "",
    var player2Id: String = ""
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            ProjectTicTacToeTheme {
                TicTacToe()
            }
        }
    }
}

@Composable
fun TicTacToe() {
    val navController = rememberNavController()
    val model = GameModel()
    model.initGame()

    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen(navController, model) }
        composable("MenuScreen") { MenuScreen(navController, model) }
        composable("GameScreen/{gameId}") { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            GameScreen(navController, model, gameId.toString()) }
    }
}