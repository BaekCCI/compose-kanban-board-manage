package woowacourse.kanban.board.domain.model

class KanbanWorkspace(private val _projectTasks: MutableList<KanbanProject> = mutableListOf()) {

    val projectTasks: List<KanbanProject> get() = _projectTasks

    fun addTask(
        title: String,
        description: String,
        tags: List<String>,
        assignee: Assignee,
        status: Status,
        projectId: String,
    ): Result<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        return try {
            val newTask = Task(
                title = title,
                description = description,
                tags = Tags(tags.map { Tag(it) }),
                assignee = assignee,
                status = status,
            )
            _projectTasks[idx] = _projectTasks[idx].addTask(newTask)
            Result.success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }

    fun deleteTask(projectId: String, task: Task): Result<Unit> {
        if (!task.status.canDeleteTask) return Result.failure(IllegalArgumentException("해당 상태에서는 태스크 삭제가 불가합니다."))

        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        _projectTasks[idx] = _projectTasks[idx].deleteTask(task.id)
        return Result.success(Unit)
    }

    fun editTask(projectId: String, task: Task, newTask: Task): Result<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        _projectTasks[idx] = _projectTasks[idx].editTask(task.id, newTask)
        return Result.success(Unit)
    }

    fun updateTaskStatus(projectId: String, task: Task, newStatus: Status): Result<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        return try {
            Status.validChangeStatus(task.status, newStatus, task.assignee != null)
            _projectTasks[idx] = _projectTasks[idx].updateStatus(task.id, newStatus)

            Result.success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }
}
