package woowacourse.kanban.board.study

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.wtc
import org.jetbrains.compose.resources.painterResource

@Composable
fun CheckerView(checked: Boolean, check: () -> Unit) {
    Column {
        Checkbox(
            checked = checked,
            onCheckedChange = { check() },
        )
        if (checked) Text(text = "checked!!")
    }
}

@Composable
@Preview
fun GreetingPreview() {
    Column(
        modifier = Modifier.background(Color.White),
    ) {
        TextExample()
        ImageExample()
        IconExample()
        ButtonExample()
        SaveButtonExample()
        ImgButtonExample()
        LikeButtonExample()
        SimpleBox()
        BoxAlignmentExample()
    }
}

@Composable
fun TextExample() {
    Text(
        "Jetpack Compost Text 실습",
        color = Color.Blue,
        fontSize = 14.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun ImageExample() {
    Image(
        painter = painterResource(resource = Res.drawable.wtc),
        contentDescription = "이미지",
    )
}

@Composable
fun IconExample() {
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "아이콘",
    )
}

@Composable
fun ButtonExample() {
    Button(
        onClick = {
            println("clicked!")
        },
    ) {
        Text(text = "press")
    }
}

@Composable
fun SaveButtonExample() {
    Button(
        onClick = {
            println("saved")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White,
        ),
    ) {
        Text(text = "저장")
    }
}

@Composable
fun ImgButtonExample() {
    Button(
        onClick = {
            println("heart")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Cyan,
            contentColor = Color.Red,
        ),
    ) {
        Image(imageVector = Icons.Default.Favorite, contentDescription = "heart")
    }
}

@Composable
fun LikeButtonExample() {
    Button(
        onClick = {
            println("like")
        },
    ) {
        Row {
            IconExample()
            Text("좋아요")
        }
    }
}

@Composable
fun SimpleBox() {
    Box(
        modifier = Modifier.size(120.dp),
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue)
                .align(Alignment.TopStart),
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Green)
                .align(Alignment.BottomEnd),
        )
    }
}

@Composable
fun BoxAlignmentExample() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
                .align(Alignment.TopStart),
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Yellow)
                .align(Alignment.Center),
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Green)
                .align(Alignment.BottomEnd),
        )
    }
}
