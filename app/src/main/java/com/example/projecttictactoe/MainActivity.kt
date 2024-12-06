package com.example.projecttictactoe

//import android.content.Intent
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
    // Shares an instance of GameModel across the entire activity
    private val gameModel: GameModel by viewModels()

    /*override fun onStop() {
        super.onStop()
        val intent = Intent(this, MediaService::class.java)
        stopService(intent) // Stops the service when the app goes into the background
    }
    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, MediaService::class.java)
        stopService(intent) // Completely stop the music when the app is closed
    }
     */
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
        // Defines GameScreen as a screen with an argument (gameId)
        composable("GameScreen/{gameId}") { backStackEntry ->
            // Retrieves gameId from the arguments passed to the screen
            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
            GameScreen(navController, gameModel, gameId)
        }
    }
}