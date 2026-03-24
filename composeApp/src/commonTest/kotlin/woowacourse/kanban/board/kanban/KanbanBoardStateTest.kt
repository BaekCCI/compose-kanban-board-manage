package woowacourse.kanban.board.kanban

import kotlin.test.Test
import kotlin.test.assertEquals
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.board.TaskBoardState

class KanbanBoardStateTest {

    private val task = Task(
        title = "태스크",
        tags = Tags(),
        user = User("테스터"),
        status = Status.TODO,
    )

    @Test
    fun `태스크 추가 시 totalCount가 증가한다`() {
        val state = TaskBoardState()

        state.createTask(task)

        assertEquals(1, state.totalCount)
    }

    @Test
    fun `Done 상태 태스크 추가 시 completeCount가 증가한다`() {
        val state = TaskBoardState()

        state.createTask(task.copy(status = Status.DONE))

        assertEquals(1, state.completeCount)
    }

    @Test
    fun `태스크가 없을 때 completeRatio는 0이다`() {
        val state = TaskBoardState()

        assertEquals(0f, state.completeRatio)
    }

    @Test
    fun `전체 태스크 중 Done 비율이 올바르게 계산된다`() {
        val state = TaskBoardState()

        state.createTask(task.copy(status = Status.DONE))
        state.createTask(task.copy(status = Status.TODO))

        assertEquals(0.5f, state.completeRatio)
    }
}
