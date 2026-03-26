package woowacourse.kanban.board.domain.model

data class ProjectGroup(val projects: List<KanbanProject>, val currentProjectId: Long) {
    init {
        require(projects.any { it.id == currentProjectId })
    }

    val selectedProject get() = projects.first { it.id == currentProjectId }

    fun addTask(task: Task): ProjectGroup = ProjectGroup(
        projects.map { project ->
            if (project.id == currentProjectId) {
                project.copy(tasks = project.tasks + task)
            } else {
                project
            }
        },
        currentProjectId,
    )

    fun changeTaskStatus(task: Task, newStatus: Status): ProjectGroup {
        val newTasks = selectedProject.tasks.map { originalTask ->
            if (task == originalTask) {
                originalTask.copy(status = newStatus)
            } else {
                originalTask
            }
        }
        val newProjects = projects.map { project ->
            if (project.id == currentProjectId) {
                project.copy(tasks = newTasks)
            } else {
                project
            }
        }
        return ProjectGroup(newProjects, currentProjectId)
    }

    fun changeProject(id: Long): ProjectGroup {
        return ProjectGroup(projects, id)
    }
}
