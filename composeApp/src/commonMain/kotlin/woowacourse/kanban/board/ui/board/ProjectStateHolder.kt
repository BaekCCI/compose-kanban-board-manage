package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_error_create_new_task
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.ProjectGroup
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task
import woowacourse.kanban.board.domain.model.User
import woowacourse.kanban.board.ui.util.SnackBarEvent

@Stable
class ProjectStateHolder(initialProjects: List<KanbanProject> = emptyList(), initialTasks: List<Task> = emptyList()) {
    private val projectGroup = ProjectGroup(initialProjects, initialTasks)

    var projects: List<KanbanProject> by mutableStateOf(projectGroup.projects)
        private set
    var selectedProjectId: Long by mutableLongStateOf(projects.first().id)
        private set
    var tasks: List<Task> by mutableStateOf(emptyList())
        private set
    var snackBarEvent: SnackBarEvent? by mutableStateOf(null)
        private set

    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()

    init {
        loadTasks(selectedProjectId)
    }

    fun changeProject(projectId: Long) {
        selectedProjectId = projectId
        loadTasks(projectId)
    }

    fun addTask(title: String, description: String, tags: List<String>, assignee: User, status: Status) {
        val result = projectGroup.addTask(
            title = title,
            description = description,
            tags = tags,
            assignee = assignee,
            status = status,
            projectId = selectedProjectId,
        )

        result.onSuccess { newTasks ->
            tasks = newTasks
            snackBarEvent = SnackBarEvent(strRes = Res.string.snackbar_create_new_task)
        }.onFailure { exception ->
            snackBarEvent = SnackBarEvent(
                strRes = Res.string.snackbar_error_create_new_task,
                message = exception.message,
            )
        }
    }

    fun changeTaskStatus(task: Task, newStatus: Status) {
        val result = projectGroup.changeTaskStatus(selectedProjectId, task, newStatus)

        result.onSuccess { newTasks ->
            tasks = newTasks
        }.onFailure { exception ->
            snackBarEvent = SnackBarEvent(message = exception.message)
        }
    }

    private fun loadTasks(projectId: Long) {
        val result = projectGroup.getTasks(projectId)

        result.onSuccess { newTasks ->
            tasks = newTasks
        }.onFailure { exception ->
            snackBarEvent = SnackBarEvent(message = exception.message)
        }
    }
}
