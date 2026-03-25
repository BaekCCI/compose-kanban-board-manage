package woowacourse.kanban.board.domain.model

data class ProjectGroup(val projects: List<KanbanProject>, val currentProjectName: String) {
    init {
        require(projects.any { it.name == currentProjectName })
    }

    val selectedProject get() = projects.first { it.name == currentProjectName }

    fun addTask(task: Task): ProjectGroup = ProjectGroup(
        projects.map { project ->
            if (project.name == currentProjectName) {
                KanbanProject(name = project.name, tasks = project.tasks + task)
            } else {
                project
            }
        },
        currentProjectName,
    )

    fun changeProject(name: String): ProjectGroup {
        return ProjectGroup(projects, name)
    }
}
