package woowacourse.kanban.board.domain

import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.KanbanWorkspace
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Tags
import woowacourse.kanban.board.domain.model.Task
import kotlin.test.Test

class KanbanWorkspaceTest {

    @Test
    fun `태스크 추가 성공 시 Result Success를 반환한다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))

        val result = workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "1")

        assert(result.isSuccess)
    }

    @Test
    fun `태스트 추가 성공 시 프로젝트 리스트에 반영된다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))
        workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "1")

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.size == 1)
    }

    @Test
    fun `태스크 추가 실패 시 Result Failure를 반환한다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))

        val result = workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "2")

        assert(result.isFailure)
    }

    @Test
    fun `태스트 추가 실패 시 프로젝트 리스트에 반영되지 않는다`() {
        val workspace = KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트")))

        workspace.addTask("타이틀", "내용", emptyList(), null, Status.TODO, "2")

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.isEmpty())
    }

    @Test
    fun `태스크 삭제 성공 시 Result Success를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.deleteTask("1", target)

        assert(result.isSuccess)
    }

    @Test
    fun `태스크 삭제 성공 시 프로젝트 리스트에 반영된다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.deleteTask("1", target)

        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.isEmpty())
    }

    @Test
    fun `태스크 삭제 실패 시 Result Failure를 반환한다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        val result = workspace.deleteTask("2", target)

        assert(result.isFailure)
    }

    @Test
    fun `태스트 삭제 실패 시 프로젝트 리스트에 반영되지 않는다`() {
        val target = Task("1", "태스크", tags = Tags(), status = Status.TODO)
        val workspace =
            KanbanWorkspace(mutableListOf(KanbanProject("1", "프로젝트", listOf(target))))

        workspace.deleteTask("2", target)
        assert(workspace.projectTasks.find { it.id == "1" }!!.tasks.size == 1)
    }
}
