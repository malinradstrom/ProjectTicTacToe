package com.example.projecttictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
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

    androidx.navigation.compose.NavHost( // Använd androidx.navigation.compose.NavHost
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen1(navController) }
        composable("MenuScreen") { MenuScreen1(navController) }
        composable("GameScreen") { GameScreen1(navController) }
    }
}

@SuppressLint("NewApi")

@Composable
fun GameScreen1(navController: NavController) {
    GameScreen(navController = navController as NavHostController)
}

@Composable
fun MenuScreen1(navController: androidx.navigation.NavController) {
    MenuScreen(Modifier)
}

@Composable
fun HomeScreen1(navController: androidx.navigation.NavController) {
    HomeScreen(Modifier)
}
