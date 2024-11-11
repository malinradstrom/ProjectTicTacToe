package com.example.projecttictactoe

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
                    color = Color(0xfff5f5f5),
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
}}