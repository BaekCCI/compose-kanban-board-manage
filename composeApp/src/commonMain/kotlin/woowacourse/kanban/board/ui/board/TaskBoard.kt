package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.model.Status

@Composable
fun TaskBoard(modifier: Modifier = Modifier, onClickCreate: () -> Unit = {}, uiState: TaskBoardState) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().background(Color(0xffF9FAFB)),
    ) {
        KanbanHeader(
            onClickCreate = onClickCreate,
            totalCount = uiState.totalCount,
            completeCount = uiState.completeCount,
            completeRatio = uiState.completeRatio,
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Status.entries.forEach { status ->
                TaskBox(
                    modifier = Modifier.weight(1f, fill = false).widthIn(max = 320.dp).fillMaxHeight(),
                    status = status,
                    tasks = uiState.tasks.filter { it.status == status },
                    boxColor = status.getBoxColor(),
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 800)
@Composable
private fun TaskBoardPreview() {
    TaskBoard(uiState = TaskBoardState())
}
