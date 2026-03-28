package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.ui.board.KanbanBoardScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        KanbanBoardScreen(
            projects = listOf(KanbanProject(name = "project1"),KanbanProject(name = "project2")),
            tasks = emptyList(),
        )
    }
}
