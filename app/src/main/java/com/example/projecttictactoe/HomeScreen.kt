package com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    navController: NavController,
    model: GameModel = viewModel(),
) {
    var inputName by remember { mutableStateOf("") }

    // When player is not created yet, show the username input and create player button
    if (model.myPlayerId.value == null) {
        LaunchedEffect(Unit) {
            model.myPlayerId.value?.let { playerId ->
                model.db.collection("players").document(playerId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val fetchedName = documentSnapshot.getString("name") ?: "Player"
                            model.username.value = fetchedName
                        }
                    }
            }
        }

        Scaffold (
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xffc1aeca),
        ) { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title()
                Spacer(modifier = Modifier.height(16.dp))
                // Input field for the username
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Enter Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Create Player Button
                OutlinedButton(
                    onClick = {
                        if (inputName.isNotBlank()) {
                            model.createPlayer(inputName) { newPlayerId ->
                                navController.navigate("MenuScreen")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),
                    border = BorderStroke(1.dp, Color(0xff2c2c2c)),
                ) {
                    Text(
                        text = "Create Player",
                        color = Color(0xfff5f5f5),
                        style = TextStyle(fontSize = 24.sp)
                    )
                }
            }
        }
    } else {
        // When player exists, show the username and "Go to Menu" button
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xffc1aeca),
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title()
                Spacer(modifier = Modifier.height(16.dp))

                // Collecting the username from the StateFlow
                val playerName by model.username.collectAsState()
                // Display the username
                UsernameText(playerName ?:"Unknown")

                Spacer(modifier = Modifier.height(20.dp))

                // Button to navigate to MenuScreen
                OutlinedButton(
                    onClick = { navController.navigate("MenuScreen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),
                    contentPadding = PaddingValues(all = 12.dp),
                    border = BorderStroke(1.dp, Color(0xff2c2c2c)),
                ) {
                    Text(
                        text = "Go to Menu",
                        color = Color(0xfff5f5f5),
                        lineHeight = 6.25.em,
                        style = TextStyle(fontSize = 24.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text(
        text = "Tic Tac Toe",
        color = Color(0xff1e1e1e),
        textAlign = TextAlign.Center,
        lineHeight = 1.67.em,
        fontStyle = FontStyle.Italic,
        style = TextStyle(
            fontSize = 78.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(color = Color.White,
                offset = Offset(6f, 10f),
                blurRadius = 4f)),
        modifier = modifier
            .offset(x = 1.dp, y = (-40).dp)
            .requiredWidth(width = 364.dp))
}

@Composable
fun UsernameText(playerName: String) {
    val displayName = if (playerName.isNotEmpty()) playerName else "Fetching username..."
    Text(
        text = "Hello, $displayName",
        color = Color(0xfff5f5f5),
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.Center,
        lineHeight = 1.05.em,
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(color = Color.DarkGray,
                offset = Offset(4f, 6f),
                blurRadius = 4f
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp, bottom = 16.dp)
    )
}


@Preview(showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val model = GameModel() // Mock
    // Provide mock player data
    model.myPlayerId.value = "mockPlayerId"

    HomeScreen(navController = navController, model = model)
}