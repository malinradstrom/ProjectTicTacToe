import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@RequiresApi(Build.VERSION_CODES.O)
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
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .rotate(degrees = -90f))
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize())
        Divider(
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
                .border(shape = CircleShape,
                    border = BorderStroke(
                    width = 1.dp, color = Color.White
                )))
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
fun TextStyle(fontSize: TextUnit, fontWeight: FontWeight) {
    TODO("Not yet implemented")
}

@Composable
fun BorderStroke(x0: Dp, x1: White) {
    TODO("Not yet implemented")
}

@Composable
fun Rectangle1(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(start = -0.6666666865348816.dp,
                end = 0.6666666865348816.dp,
                top = -0.6486486196517944.dp,
                bottom = 0.6486486196517944.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(14.dp))
                .background(color = Color(0xff5d4b65)))
    }
}

@Composable
fun RoundedCornerShape(x0: Dp) {
    TODO("Not yet implemented")
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(widthDp = 60, heightDp = 60)
@Composable
private fun AppIconPreview() {
    AppIcon(Modifier)
}