package woowacourse.kanban.board.domain.model

import java.util.UUID

data class KanbanProject(val id: UUID = UUID.randomUUID(), val name: String)
