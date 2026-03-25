package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.ui.board.ProjectState

@Composable
@Preview
fun App() {
    MaterialTheme {
        KanbanBoardScreen(
            initialProjectState = ProjectState(
                listOf(KanbanProject("A프로젝트"), KanbanProject("B프로젝트"), KanbanProject("C프로젝트")),
                KanbanProject("A프로젝트"),
            ),
        )
    }
}
