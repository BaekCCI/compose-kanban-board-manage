package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.ProjectGroup
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task

@Stable
class ProjectState(val projects: List<KanbanProject>, initialProjectId: Long) {
    var projectGroup: ProjectGroup by mutableStateOf(ProjectGroup(projects, initialProjectId))
        private set

    val totalCount: Int get() = projectGroup.selectedProject.tasks.size
    val completeCount: Int get() = projectGroup.selectedProject.tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()

    fun createTask(task: Task) {
        projectGroup = projectGroup.addTask(task)
    }

    fun selectProject(projectId: Long) {
        projectGroup = projectGroup.changeProject(projectId)
    }

    fun changeTaskStatus(task: Task, newStatus: Status) {
        projectGroup = projectGroup.changeTaskStatus(task, newStatus)
    }
}
