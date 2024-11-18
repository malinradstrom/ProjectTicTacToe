package com.example.projecttictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.ui.theme.ProjectTicTacToeTheme

data class TicTacToe(
    val id: Int,
    var title: String,
    val check: MutableState<Boolean> = mutableStateOf(false)
)

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
    val navController = rememberNavController()
    val tictactoeList = remember { mutableListOf<TicTacToe>() }
    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen1(navController, tictactoeList) }
        composable("MenuScreen") { MenuScreen1(navController, tictactoeList) }
        composable("GameScreen") { GameScreen1(navController, tictactoeList) }
    }
}

@SuppressLint("NewApi")

@Composable
fun GameScreen1(navController: NavController, tictactoeList: MutableList<TicTacToe>) {
    GameScreen(navController)
}

@Composable
fun MenuScreen1(navController: NavController, tictactoeList: MutableList<TicTacToe>) {
    MenuScreen(navController)
}

@Composable
fun HomeScreen1(navController: NavController, tictactoeList: MutableList<TicTacToe>) {
    HomeScreen(navController)
}