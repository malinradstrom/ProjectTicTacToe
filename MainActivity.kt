package com.example.projecttictactoe
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.ui.theme.ProjectTicTacToeTheme


class MainActivity : ComponentActivity() {
    // Delar en instans av GameModel över hela aktiviteten
    private val gameModel: GameModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTicTacToeTheme {
                TicTacToe(gameModel)
            }
        }
    }
}

@Composable
fun TicTacToe(gameModel: GameModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen(navController, gameModel) }
        composable("MenuScreen") { MenuScreen(navController, gameModel) }
        // Definierar GameScreen som en skärm med ett argument (gameId)
        composable("GameScreen/{gameId}") { backStackEntry ->
            // Hämtar gameId från argumenten som skickas till skärmen
            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
            GameScreen(navController, gameModel, gameId)
        }
    }
}
