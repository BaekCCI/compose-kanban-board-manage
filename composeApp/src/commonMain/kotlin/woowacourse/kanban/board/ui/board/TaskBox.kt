package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.util.toUiString

@Composable
fun TaskBox(modifier: Modifier = Modifier, status: Status, tasks: List<Task>, boxColor: TaskBoxColor) {
    Column(
        modifier = modifier.clip(shape = RoundedCornerShape(10.dp))
            .background(boxColor.background),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(boxColor.headerBackground)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(status.toUiString()), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                tasks.size.toString(),
                modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(Color.White).padding(horizontal = 10.dp, vertical = 4.dp),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().border(
                width = 1.dp,
                color = boxColor.border,
                shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp),
            ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(tasks, key = { it.id }) {
                TaskCard(task = it, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

data class TaskBoxColor(val background: Color, val headerBackground: Color, val border: Color)

fun Status.getBoxColor(): TaskBoxColor = when (this) {
    Status.TODO -> TaskBoxColor(
        background = Color(0xffEFF6FF),
        headerBackground = Color(0xff155DFC),
        border = Color(0xffBEDBFF),
    )

    Status.IN_PROGRESS -> TaskBoxColor(
        background = Color(0xffFFFBEB),
        headerBackground = Color(0xffE17100),
        border = Color(0xffFEE685),
    )

    Status.DONE -> TaskBoxColor(
        background = Color(0xffF0FDF4),
        headerBackground = Color(0xff00A63E),
        border = Color(0xffB9F8CF),
    )
}

@Preview
@Composable
private fun TaskBoxPreview() {
    TaskBox(
        status = Status.DONE,
        tasks = listOf(
            Task(title = "Task 1", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 2", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 3", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 1", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 2", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 3", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 1", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 2", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
            Task(title = "Task 3", description = "asdfasd", tags = Tags(emptyList()), user = User("dino"), status = Status.TODO),
        ),
        boxColor = Status.DONE.getBoxColor(),
    )
}
