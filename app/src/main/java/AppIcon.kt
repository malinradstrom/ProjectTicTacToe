package com.example.projecttictactoe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projecttictactoe.R.drawable.appicon


@Composable
fun AppIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = appicon),
        contentDescription = "App Icon",
        modifier = modifier
            .requiredSize(size = 60.dp))
}

@Preview(widthDp = 60, heightDp = 60)
@Composable
private fun AppIconPreview() {
    AppIcon(Modifier)
}