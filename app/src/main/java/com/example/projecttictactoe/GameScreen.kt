package com.example.projecttictactoe.com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.projecttictactoe.R


@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 917.dp)
            .clip(shape = RoundedCornerShape(30.dp))
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
            ) { AlignCenter1() }
        }
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 57.5.dp,
                    y = 348.dp)
                .requiredSize(size = 300.dp)
                .background(color = Color.White.copy(alpha = 0.6f))
                .border(border = BorderStroke(2.dp, Color.Black)))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 57.dp,
                    y = 348.dp)
                .requiredWidth(width = 301.dp)
                .requiredHeight(height = 345.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 6.530029296875.dp,
                        y = 6.dp)
                    .requiredSize(size = 95.dp))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 199.5.dp,
                        y = 6.dp)
                    .requiredSize(size = 95.dp)
                    .rotate(degrees = -90f))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 199.5.dp,
                        y = 199.dp)
                    .requiredSize(size = 95.dp)
                    .rotate(degrees = -180f))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 6.5.dp,
                        y = 199.dp)
                    .requiredSize(size = 95.dp)
                    .rotate(degrees = 90f))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 100.5.dp,
                        y = 0.dp)
                    .requiredSize(size = 100.dp))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 200.5.dp,
                        y = 100.dp)
                    .requiredSize(size = 100.dp))
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 100.5.dp,
                        y = 100.dp)
                    .requiredSize(size = 100.dp)
                    .border(border = BorderStroke(2.dp, Color.Black)))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp,
                        y = 100.dp)
                    .requiredSize(size = 100.dp))
            Spacer(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 101.dp,
                        y = 200.dp)
                    .requiredSize(size = 100.dp))
            Text(
                text = "Player x Turn",
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 6.em,
                style = TextStyle(
                    fontSize = 20.sp),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 74.5.dp,
                        y = 313.dp)
                    .requiredWidth(width = 149.dp)
                    .requiredHeight(height = 32.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.menu),
            contentDescription = "Menu",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 355.dp,
                    y = 67.dp)
                .requiredWidth(width = 37.dp)
                .requiredHeight(height = 52.dp))
    }
}

@Composable
fun AlignCenter1(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Tic Tac Toe",
            color = Color(0xff1e1e1e),
            textAlign = TextAlign.Center,
            lineHeight = 3.75.em,
            style = MaterialTheme.typography.headlineLarge,
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

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun GameScreenPreview() {
    GameScreen(Modifier)
}