package com.example.projecttictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.ui.theme.ProjectTicTacToeTheme

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
