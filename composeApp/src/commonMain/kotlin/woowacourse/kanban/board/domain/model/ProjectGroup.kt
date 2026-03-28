package woowacourse.kanban.board.domain.model

class ProjectGroup(initialProjects: List<KanbanProject> = emptyList(), initialTasks: List<Task> = emptyList()) {
    private val _projects: MutableList<KanbanProject> = initialProjects.toMutableList()
    val projects get() = _projects.toList()

    private val projectTasks: MutableMap<Long, MutableList<Task>> =
        initialProjects.associate { project ->
            project.id to initialTasks.filter { task ->
                task.projectId == project.id
            }.toMutableList()
        }.toMutableMap()

    fun addTask(projectId: Long, task: Task): Result<List<Task>> {
        val tasks = projectTasks[projectId] ?: return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        tasks.add(task)
        return Result.success(tasks.toList())
    }

    fun changeTaskStatus(projectId: Long, task: Task, newStatus: Status): Result<List<Task>> {
        val tasks = projectTasks[projectId] ?: return Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))

        val idx = tasks.indexOfFirst { it.id == task.id }
        if (idx == -1) return Result.failure(IllegalArgumentException("$task 태스크를 찾을 수 없습니다."))

        tasks[idx] = task.copy(status = newStatus)
        return Result.success(tasks.toList())
    }

    fun getTasks(projectId: Long): Result<List<Task>> {
        return projectTasks[projectId]?.let { Result.success(it.toList()) }
            ?: Result.failure(IllegalArgumentException("프로젝트(id = $projectId)를 찾을 수 없습니다."))
    }
}
