package woowacourse.kanban.board.domain.model

class KanbanWorkspace(private val _projectTasks: MutableList<KanbanProject> = mutableListOf()) {

    val projectTasks: List<KanbanProject> get() = _projectTasks

    fun addTask(title: String, description: String, tags: List<String>, assignee: User, status: Status, projectId: String): Result<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        return try {
            val newTask = Task(
                title = title,
                description = description,
                tags = Tags(tags.map { Tag(it) }),
                user = assignee,
                status = status,
            )
            _projectTasks[idx] = _projectTasks[idx].addTask(newTask)
            Result.success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }

    fun updateTaskStatus(projectId: String, task: Task, newStatus: Status): Result<Unit> {
        val idx = _projectTasks.indexOfFirst { it.id == projectId }
        if (idx == -1) return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        return try {
            _projectTasks[idx] = _projectTasks[idx].updateStatus(task.id, newStatus)
            Result.success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }
}
