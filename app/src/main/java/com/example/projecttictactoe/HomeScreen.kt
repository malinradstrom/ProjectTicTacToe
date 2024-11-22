package com.example.projecttictactoe

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projecttictactoe.com.example.projecttictactoe.GameModel
import kotlin.String

@Composable
fun TicTacToe() {
    val navController = rememberNavController()
    val model = GameModel()
    model.initGame()

    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {
        composable("HomeScreen") { HomeScreen(navController, model) }
        composable("MenuScreen") { MenuScreen(navController, model) }
        composable("GameScreen/{gameId}") { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            GameScreen(navController, model, gameId) }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    //tictactoeList: MutableList<String>,
    model: GameModel
) {
    //var userName by remember { mutableStateOf("") }
    //var isEditing by remember { mutableStateOf(true) }
    val sharedPreferences = LocalContext.current.getSharedPreferences("TicPrefs", Context.MODE_PRIVATE)
    LaunchedEffect(Unit) {
        model.myPlayerId.value = sharedPreferences.getString("playerId", null)
        if (model.myPlayerId.value != null) {
            navController.navigate("MenuScreen")
        }
    }
    if (model.myPlayerId.value == null) {
        var playerName by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .requiredWidth(width = 412.dp)
                .requiredHeight(height = 917.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = Color(0xffc1aeca))
        ) {
            Title()
            StartGameButton(navController, Modifier)
            UsernameInputField(
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
            )
        }
    }
}


@Composable
fun StartGameButton(navController: NavController,modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { navController.navigate("MenuScreen") },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),
        contentPadding = PaddingValues(all = 12.dp),
        border = BorderStroke(1.dp, Color(0xff2c2c2c)
        ),
        modifier = modifier
            .offset(x = 112.dp,
                y = 591.dp)
            .requiredWidth(width = 188.dp)
            .requiredHeight(height = 78.dp)

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
                text = "Save Player",
                color = Color(0xfff5f5f5),
                lineHeight = 6.25.em,
                style = TextStyle(
                    fontSize = 24.sp))
        }
    }
}

@Composable
fun UsernameInputField(userName: String,
                       onUserNameChange: (String) -> Unit,
                       onUserNameSave: () -> Unit,
                       isEditing: Boolean,
                       modifier: Modifier = Modifier) {
    // State variables to hold the username and edit mode
    Box(modifier) {
        if (isEditing) {
            OutlinedTextField(
                value = userName,
                onValueChange = onUserNameChange,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onUserNameSave() // Save username on Enter key press
                }),
                placeholder = {
                    Text(
                        text = "Enter Username",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Gray)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .offset(x = 85.dp,
                        y = 403.dp)
                    .requiredWidth(width = 210.dp)
            )
        } else {
            // Display the saved username when not in editing mode
            Text(
                text = userName.ifEmpty { "Enter Username" },
                color = Color.Black,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Gray)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .offset(x = 85.dp,
                        y = 403.dp)
                    .requiredWidth(width = 210.dp)
                    .clickable { onUserNameSave() } // Re-enter editing mode when tapped
            )
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
            .offset(x=24.dp, y = 99.dp)
            .requiredWidth(width = 364.dp))
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()
    val tictactoeList = remember { mutableStateListOf<String>() }

    HomeScreen(navController = navController, tictactoeList = tictactoeList)
}