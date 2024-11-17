package com.example.projecttictactoe


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(navController: Modifier.Companion, modifier: Modifier = Modifier) {
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
                    Tab(
                        selected = false,
                        onClick = {  },
                        text = {
                            Text(
                                text = "Game Requests ",
                                color = Color(0xfff5f5f5),
                                textAlign = TextAlign.Center,
                                lineHeight = 3.16.em,
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 38.sp,
                                    shadow = Shadow(color = Color.Black.copy(alpha = 0.4000000059604645f), offset = Offset(0f, 4f), blurRadius = 4f)))
                        })
                    Column(
                        modifier = Modifier
                            .requiredWidth(width = 360.dp)
                            .requiredHeight(height = 509.dp)
                            .background(color = Color(0xffe7e0ec))
                    ) {
                        repeat(8) {
                            Condition1LineLeadingMonogramTrailingCheckBoxShowOverlineFalseSho()
                        }

                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "arrow_back",
                    colorFilter = ColorFilter.tint(Color(0x11F5EFF7)),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 48.dp,
                            y = 57.dp)
                        .requiredSize(size = 45.dp)
                        .clip(shape = RoundedCornerShape(30.dp)))
            }
        }
    }
}

@Composable
fun Condition1LineLeadingMonogramTrailingCheckBoxShowOverlineFalseSho(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 56.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 360.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 56.dp)
                    .padding(horizontal = 16.dp,
                        vertical = 8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    BuildingBlocksMonogram()
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f)
                ) {
                    Text(
                        text = "List item",
                        color = Color(0xff1d1b20),
                        lineHeight = 1.5.em,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(align = Alignment.CenterVertically))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TypeSelectedStateEnabled()
                }
            }
        }
        BuildingBlocksstatelayer1Enabled()
    }
}

@Composable
fun BuildingBlocksMonogram(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredSize(size = 40.dp)
            .clip(shape = RoundedCornerShape(100.dp))
            .background(color = Color.White)
    ) {
        Text(
            text = "A",
            color = Color(0xff4f378a),
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.15.sp),
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .offset(x = 0.dp,
                    y = 0.dp)
                .requiredSize(size = 40.dp)
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}

@Composable
fun TypeSelectedStateEnabled(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(end = 4.dp,
                top = 4.dp,
                bottom = 4.dp)
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(100.dp))
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(all = 11.dp)
                ) {
                    val checkedState = remember { mutableStateOf(true) }
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it })
                }
                Icon(
                    painter = painterResource(id = R.drawable.check_small),
                    contentDescription = "check_small",
                    tint = Color.White,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .offset(x = 0.dp,
                            y = 0.dp))
            }
        }
    }
}

@Composable
fun BuildingBlocksstatelayer1Enabled(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize())
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
private fun MenuScreenPreview() {
    MenuScreen(Modifier)
}