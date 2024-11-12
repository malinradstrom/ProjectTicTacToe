package com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Frame2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 415.dp)
            .requiredHeight(height = 917.dp)
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 412.dp)
                .requiredHeight(height = 917.dp)
        ) {
            Column(
                modifier = Modifier
                    .requiredWidth(width = 412.dp)
                    .requiredHeight(height = 917.dp)
                    .background(color = Color.White)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xffc1aeca))
                        .padding(horizontal = 24.dp,
                            vertical = 160.dp)
                ) {
                    AlignCenter()
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(
                                text = "Multiplayer",
                                color = Color(0xff1e1e1e),
                                lineHeight = 6.25.em,
                                style = TextStyle(
                                    fontSize = 16.sp))
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White),
                        modifier = Modifier
                            .requiredWidth(width = 240.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(
                                text = "Add Name:",
                                color = Color(0xff1e1e1e),
                                lineHeight = 6.25.em,
                                style = TextStyle(
                                    fontSize = 16.sp))
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White),
                        modifier = Modifier
                            .requiredWidth(width = 240.dp))
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 124.dp,
                        y = 612.dp)
                    .requiredWidth(width = 165.dp)
                    .requiredHeight(height = 68.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = Color(0xff2c2c2c))
                    .border(border = BorderStroke(1.dp, Color(0xff2c2c2c)),
                        shape = RoundedCornerShape(8.dp))
                    .padding(all = 12.dp)
            ) {
                Text(
                    text = "Start Game",
                    color = Color(0xFFFFFEFF),
                    lineHeight = 6.25.em,
                    style = TextStyle(
                        fontSize = 16.sp))
            }
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Menu",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 356.dp,
                        y = 140.dp)
                    .requiredWidth(width = 37.dp)
                    .requiredHeight(height = 52.dp))
        }
    }
}

@Composable
fun CustomRow(
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun AlignCenter(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Tic Tac Toe",
            color = Color(0xff1e1e1e),
            textAlign = TextAlign.Center,
            lineHeight = 1.67.em,
            style = TextStyle(
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth())
        Text(
            text = "",
            color = Color(0xff757575),
            textAlign = TextAlign.Center,
            lineHeight = 3.75.em,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Preview(widthDp = 415, heightDp = 917)
@Composable
private fun Frame2Preview() {
    Frame2(Modifier)
}