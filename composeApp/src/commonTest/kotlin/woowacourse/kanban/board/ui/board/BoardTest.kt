package woowacourse.kanban.board.ui.board

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.domain.model.KanbanProject

@OptIn(ExperimentalTestApi::class)
class BoardTest {

    @Test
    fun `사용자가 프로젝트 선택하면 해당하는 해당 프로젝트 화면으로 전환된다`() = runComposeUiTest {

        // Given 현재 프로젝트가 A프로젝트
        val initialProject = KanbanProject("A 프로젝트")
        val state = ProjectState(listOf(KanbanProject("A 프로젝트"), KanbanProject("B 프로젝트")), initialProject)

        // When B 프로젝트를 선택한다.
        setContent {
            KanbanBoardScreen(
                initialProjectState = state,
            )
        }
        onNodeWithContentDescription(label = "A 프로젝트 화면").assertIsDisplayed()
        onNodeWithContentDescription(label = "B 프로젝트 전환 버튼").performClick()

        // Then B 프로젝트에 대한 태스크 목록이 표시된다
        onNodeWithContentDescription(label = "A 프로젝트 화면").assertDoesNotExist()
        onNodeWithContentDescription(label = "B 프로젝트 화면").assertIsDisplayed()
    }
}
