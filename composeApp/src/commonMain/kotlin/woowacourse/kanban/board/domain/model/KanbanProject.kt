package woowacourse.kanban.board.domain.model

data class KanbanProject(val id: Long, val name: String, val tasks: List<Task> = emptyList())
