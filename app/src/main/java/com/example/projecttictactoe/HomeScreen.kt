package com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController,modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 917.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(color = Color(0xffc1aeca))
    ) {
        Title()
        StartGameButton(navController)
        TextareaField()
    }
}

@Composable
fun StartGameButton(navController: NavController,modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { navController.navigate("menu") },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),
        contentPadding = PaddingValues(all = 12.dp),
        border = BorderStroke(1.dp, Color(0xff2c2c2c)),
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
                text = "Start Game",
                color = Color(0xfff5f5f5),
                lineHeight = 6.25.em,
                style = TextStyle(
                    fontSize = 16.sp))
        }
    }
}

@Composable
fun TextareaField(modifier: Modifier = Modifier) {
    Box(modifier) {
        var userName by remember { mutableStateOf("") }
        Text(
            text = "User Name:",
            color = Color(0xff1e1e1e),
            lineHeight = 8.75.em,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .offset(x = 105.dp,
                    y = 403.dp)
                .fillMaxWidth())
        OutlinedTextField(
            value = userName,
            onValueChange = {newText: String -> userName = newText},
            label = {
                Text(
                    text = "Input",
                    color = Color(0xff1e1e1e),
                    lineHeight = 8.75.em,
                    style = TextStyle(
                        fontSize = 16.sp))
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White),
            modifier = Modifier
                .offset(x = 105.dp,
                    y = 403.dp)
                .requiredWidth(width = 210.dp)
                .border(border = BorderStroke(1.dp, Color(0xffd9d9d9)),
                    shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp,
                    vertical = 12.dp))
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text(
        text = "Tic Tac Toe",
        color = Color(0xff1e1e1e),
        textAlign = TextAlign.Center,
        lineHeight = 1.67.em,
        style = TextStyle(
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(color = Color.White,
                offset = Offset(0f, 4f),
                blurRadius = 4f)),
        modifier = modifier
            .offset(x=24.dp, y = 99.dp)
            .requiredWidth(width = 364.dp))
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun HomeScreenPreview() {
    // Use rememberNavController to create a valid NavController instance
    val navController = rememberNavController()
    HomeScreen(navController = navController, modifier = Modifier)
}