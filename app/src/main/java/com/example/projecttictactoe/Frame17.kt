package com.example.projecttictactoe.com.example.projecttictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.projecttictactoe.Frame17
import com.example.projecttictactoe.FrameItem

@Composable
fun Frame17(modifier: Modifier = Modifier, onGoToMenuClick: () -> Unit, onGiveUpClick: () -> Unit) {
    val gridData = listOf(FrameItem("Go to Menu"), FrameItem("Give up"))
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(-109.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(gridData.size) { index ->
                val textLabeltext = gridData[index].textLabeltext
                Button(
                    onClick = {
                        when (index) {
                            0 -> onGoToMenuClick()
                            1 -> onGiveUpClick()
                        }
                    },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff65558f)),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = textLabeltext,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 1.43.em,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        modifier = modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 211.dp)
            .background(color = Color(0xffe7e0ec))
            .padding(16.dp)
    )
}

@Preview(widthDp = 412, heightDp = 211)
@Composable
private fun Frame17Preview() {
    Frame17(onGoToMenuClick = { }, onGiveUpClick = { })
}