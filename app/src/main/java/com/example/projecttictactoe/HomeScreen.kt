package com.example.projecttictactoe
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var userName by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 917.dp)
    ) {
        Column(
            modifier = Modifier
                .requiredWidth(width = 412.dp)
                .requiredHeight(height = 917.dp)
                .background(color = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xffc1aeca))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .requiredWidth(width = 412.dp)
                        .requiredHeight(height = 917.dp)
                        .padding(horizontal = 24.dp,
                            vertical = 160.dp)
                ) {
                    AlignCenter()
                }
                TextField(
                    value = userName,
                    onValueChange = {newText: String -> userName = newText},
                    label = {
                        Text(
                            text = "User Name:",
                            color = Color(0xff49454f),
                            lineHeight = 1.33.em,
                            style = MaterialTheme.typography.bodySmall)
                    },
                    placeholder = { Text("Input") },
                    trailingIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .requiredSize(size = 48.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(1.dp))
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(all = 8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.cancelbutton),
                                            contentDescription = "Icon",
                                            tint = Color(0xff49454f))
                                    }
                                }
                            }
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        Color(0xff1d1b20),
                        Color(0xffe6e0e9)),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 101.dp,
                            y = 430.dp)
                        .requiredWidth(width = 210.dp)
                        .requiredHeight(height = 56.dp)
                        .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)))
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(18.dp), //round corners to start game button
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),
                    contentPadding = PaddingValues(all = 12.dp),
                    border = BorderStroke(1.dp, Color(0xff2c2c2c)),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 112.dp,
                            y = 602.dp)
                        .requiredWidth(width = 188.dp)
                        .requiredHeight(height = 78.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .requiredWidth(width = 188.dp)
                            .requiredHeight(height = 78.dp)
                    ) {
                        Text(
                            text = "Start Game",
                            color = Color(0xfff5f5f5),
                            lineHeight = 6.25.em,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopEnd // Align to top right
    ) {
        Menu(
            modifier = Modifier
                .padding(20.dp) // Add padding for spacing
        )
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier) {
    IconButton(
        onClick = { },
        modifier = modifier
            .size(48.dp)
            .clip(RectangleShape)
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 37.dp)
                .requiredHeight(height = 52.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Icon",
                modifier = Modifier
                    .fillMaxSize()
                    .border(border = BorderStroke(10.dp, Color.Transparent)))
        }
    }
}


@Composable
fun AlignCenter(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        Text(
            text = "Tic Tac Toe",
            color = Color(0xff1e1e1e),
            textAlign = TextAlign.Center,
            lineHeight = 1.67.em,
            style = TextStyle(
                fontSize = 72.sp,
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.White, offset = Offset(0f, 6f), blurRadius = 8f)),
            modifier = Modifier
                .fillMaxWidth())
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(Modifier)
}
