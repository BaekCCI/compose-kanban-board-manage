package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_error_create_new_task
import woowacourse.kanban.board.domain.model.Assignee
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.KanbanWorkspace
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.ui.util.SnackBarEvent

class ProjectStateHolder(initialProjects: List<KanbanProject> = emptyList()) {
    private val workspace = KanbanWorkspace(initialProjects.toMutableStateList())

    val projects get() = workspace.projectTasks
    var currentProjectId: String by mutableStateOf(projects.first().id)
        private set

    val currentProject: KanbanProject get() = projects.first { it.id == currentProjectId }
    var snackBarEvent: SnackBarEvent? by mutableStateOf(null)
        private set

    fun changeProject(projectId: String) {
        currentProjectId = projectId
    }

    fun addTask(title: String, description: String, tags: List<String>, assignee: Assignee, status: Status) {
        val result = workspace.addTask(
            title = title,
            description = description,
            tags = tags,
            assignee = assignee,
            status = status,
            projectId = currentProject.id,
        )

        result.onSuccess {
            snackBarEvent = SnackBarEvent(strRes = Res.string.snackbar_create_new_task)
        }.onFailure { exception ->
            snackBarEvent = SnackBarEvent(
                strRes = Res.string.snackbar_error_create_new_task,
                message = exception.message,
            )
        }
    }

    fun changeTaskStatus(task: Task, newStatus: Status) {
        val result = workspace.updateTaskStatus(currentProject.id, task, newStatus)

        result.onSuccess {
            snackBarEvent = SnackBarEvent(
                message = "태스크가 이동되었습니다.",
            )
        }.onFailure { exception ->
            snackBarEvent = SnackBarEvent(message = exception.message)
        }
    }
}
