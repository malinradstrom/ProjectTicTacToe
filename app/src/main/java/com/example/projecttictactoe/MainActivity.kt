package com.example.projecttictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import com.example.projecttictactoe.com.example.projecttictactoe.GameScreen
import com.example.projecttictactoe.com.example.projecttictactoe.MenuScreen
import com.example.projecttictactoe.ui.theme.ProjectTicTacToeTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTicTacToeTheme {
                TicTacToeApp()
            }
        }
    }
}

@Composable
fun TicTacToeApp() {
    val navController = androidx.navigation.compose.rememberNavController()

    androidx.navigation.compose.NavHost( // Anvand androidx.navigation.compose.NavHost
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen1(navController) }
        composable("menuScreen") { MenuScreen1(navController) }
        composable("appIcon") { AppIcon1() }
        composable("gameScreen") { GameScreen1(navController) }
    }
}

fun composable(string: String, function: @Composable () -> Unit) {}

@SuppressLint("NewApi")
@Composable
fun AppIcon1() {
    AppIcon(Modifier)
}

@Composable
fun GameScreen1(navController: androidx.navigation.NavController) {
    GameScreen(Modifier)
}

@Composable
fun MenuScreen1(navController: androidx.navigation.NavController) {
    MenuScreen(Modifier)
}

@Composable
fun HomeScreen1(navController: androidx.navigation.NavController) {
    HomeScreen(Modifier)
}