package com.example.projecttictactoe

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(onNavigateToGame: () -> Unit, onNavigateToMenu: () -> Unit) {
    Button(onClick = onNavigateToGame) {
        Text("Start Game")
    }
    Button(onClick = onNavigateToMenu) {
        Text("Menu")
    }
}

@Composable
fun GameScreen(onNavigateBack: () -> Unit) {
    // Game screen content
    Button(onClick = onNavigateBack) {
        Text("Back to Home")
    }
}

@Composable
fun MenuScreen(onNavigateBack: () -> Unit) {
    // Menu screen content
    Button(onClick = onNavigateBack) {
        Text("Back to Home")
    }
}