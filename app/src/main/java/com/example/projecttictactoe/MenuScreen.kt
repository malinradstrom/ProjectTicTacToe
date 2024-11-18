package com.example.projecttictactoe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MenuScreen(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 412.dp)
            .requiredHeight(height = 917.dp)
            .background(color = Color(0xffc1aeca))
    ) {
        Text(
            text = "Game Requests",
            color = Color(0xfff5f5f5),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            lineHeight = 3.16.em,
            style = TextStyle(
                fontSize = 38.sp,
                fontWeight = FontWeight.Light,
                shadow = Shadow(color = Color.Black.copy(alpha = 0.4000000059604645f),
                    offset = Offset(0f, 4f),
                    blurRadius = 4f)),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 24.dp,
                    y = 147.dp)
                .requiredWidth(width = 364.dp))
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 49.dp,
                    y = 54.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = Color(0xff65558f))
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 45.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "icon",
                    modifier = Modifier
                        .fillMaxSize())
            }
        }
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 59.dp,
                    y = 230.dp)
                .requiredWidth(width = 294.dp)
                .requiredHeight(height = 574.dp)
        ) {
            List2Density(navController = navController)
        }
    }
}

@Composable
fun List2Density(navController: NavController,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .requiredWidth(width = 294.dp)
            .requiredHeight(height = 575.dp)
            .background(color = Color(0xfffef7ff))
    ) {
        repeat(12) { //List connected to the fire base
            Condition1LineLeadingMonogramTrailingCheckBoxShowOverlineFalseSho(navController = navController)
        }

    }
}

@Composable
fun Condition1LineLeadingMonogramTrailingCheckBoxShowOverlineFalseSho(
    navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 48.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 294.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 48.dp)
                    .padding(horizontal = 16.dp,
                        vertical = 4.dp)
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
                    TypeSelectedStateEnabled(navController = navController)
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
            .background(color = Color(0xffeaddff))
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
fun TypeSelectedStateEnabled(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(end = 4.dp, top = 4.dp, bottom = 4.dp)
    ) {
        // Wrapping the Checkbox with an IconButton that triggers navigation
        IconButton(
            onClick = {
                // Navigate to the game screen when the checkbox is clicked
                navController.navigate("GameScreen")
            },
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
                    // Checkbox is displayed as checked but not interactable
                    Checkbox(
                        checked = true,  // Always display as checked
                        onCheckedChange = null  // Disable interaction
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.check_small),
                    contentDescription = "check_small",
                    tint = Color.Transparent,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .offset(x = 0.dp, y = 0.dp)
                )
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
    val navController = rememberNavController()
    MenuScreen(navController, Modifier)
}