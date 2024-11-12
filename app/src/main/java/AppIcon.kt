package com.example.projecttictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredSize(size = 60.dp)
    ) {
        Rectangle1()
        Image(
            painter = painterResource(id = R.drawable.line1),
            contentDescription = "Line 1",
            modifier = Modifier
                .fillMaxSize()
                .rotate(degrees = -90f)
                .border(border = BorderStroke(2.dp, Color.White)))
        HorizontalDivider(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .rotate(degrees = -90f))
        HorizontalDivider(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize())
        HorizontalDivider(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize())
        Image(
            painter = painterResource(id = R.drawable.ellipse1),
            contentDescription = "Ellipse 1",
            modifier = Modifier
                .fillMaxSize()
                .border(border = BorderStroke(1.dp, Color.White)))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)
                .background(color = Color(0xffd9d9d9))
                .border(border = BorderStroke(1.dp, Color.White),
                    shape = CircleShape))
        Text(
            text = "X",
            color = Color.White,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium),
            modifier = Modifier
                .fillMaxSize())
        Image(
            painter = painterResource(id = R.drawable.ellipse3),
            contentDescription = "Ellipse 3",
            modifier = Modifier
                .fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)
                .background(color = Color(0xff5d4b65)))
        Text(
            text = "X",
            color = Color.White,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium),
            modifier = Modifier
                .fillMaxSize())
    }
}

@Composable
fun Rectangle1(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(14.dp))
                .background(color = Color(0xff5d4b65)))
    }
}

@Preview(widthDp = 60, heightDp = 60)
@Composable
private fun AppIconPreview() {
    AppIcon(Modifier)
}