package com.example.projecttictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") {
            HomeScreen(
                onNavigateToGame = { navController.navigate("GameScreen") },
                onNavigateToMenu = { navController.navigate("MenuScreen") }
            )
        }
        composable("GameScreen") {
            GameScreen(
                onNavigateBack = { navController.popBackStack("HomeScreen", inclusive = false) }
            )
        }
        composable("MenuScreen") {
            MenuScreen(
                onNavigateBack = { navController.popBackStack("HomeScreen", inclusive = false) }
            )
        }
    }
}
/*
@SuppressLint("NewApi")

@Composable
fun GameScreen1(navController: NavController) {
    GameScreen(navController = navController as NavHostController)
}

@Composable
fun MenuScreen1(navController: NavController) {
    MenuScreen(Modifier)
}

@Composable
fun HomeScreen1(navController: NavController) {
    HomeScreen(navController)
}
*/
