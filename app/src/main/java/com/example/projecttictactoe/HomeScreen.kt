package com.example.projecttictactoe

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.com.example.projecttictactoe.GameModel

@Composable
fun HomeScreen(
    navController: NavController,
    model: GameModel
) {
    val sharedPreferences = LocalContext.current.getSharedPreferences("TicPrefs", Context.MODE_PRIVATE)
    LaunchedEffect(Unit) {
        model.myPlayerId.value = sharedPreferences.getString("playerId", null)
        if (model.myPlayerId.value != null) {
            navController.navigate("MenuScreen")
        }
    }
    if (model.myPlayerId.value == null) {
        var playerName by remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = Color(0xffc1aeca)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()
            Spacer(modifier = Modifier.height(16.dp))
            /*UsernameInputField(
                userName = userName,
                onUserNameChange = { newName ->
                    if (newName.length <= 20 && newName.all { it.isLetterOrDigit() }) {
                        userName = newName
                    }
                },
                onUserNameSave = {
                    if (userName.isNotBlank()) {
                        tictactoeList.add(userName) // Save username to the list
                        isEditing = false // Exit editing mode after saving
                    }
                },
                isEditing = isEditing,
                modifier = Modifier
                    .offset(x = 20.dp, y = 0.dp)
                    .requiredWidth(210.dp)
            )*/
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Enter Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    if (playerName.isNotBlank()) {
                        val newPlayer = Player(name = playerName)
                        model.db.collection("players").add(newPlayer).addOnSuccessListener { documentRef ->
                            val newPlayerId = documentRef.id

                            sharedPreferences.edit().putString("playerId", newPlayerId).apply()

                            model.myPlayerId.value = newPlayerId
                            navController.navigate("MenuScreen") }
                            .addOnFailureListener { error ->
                                Log.e("Error", "Error creating player: ${error.message} ")
                            }
                    } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),
                contentPadding = PaddingValues(all = 12.dp),
                border = BorderStroke(1.dp, Color(0xff2c2c2c)
                ),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp,
                        Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .requiredWidth(width = 188.dp)
                        .requiredHeight(height = 78.dp)
                ) {
                    Text(
                        text = "Create Player",
                        color = Color(0xfff5f5f5),
                        lineHeight = 6.25.em,
                        style = TextStyle(
                            fontSize = 24.sp))
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
            .offset(x=1.dp, y = (-40).dp)
            .requiredWidth(width = 364.dp))
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()
    val model = GameModel()

    HomeScreen(navController = navController, model)
}