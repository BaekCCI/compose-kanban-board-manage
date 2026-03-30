package woowacourse.kanban.board.domain.model

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val projectId: UUID,
    val title: String,
    val description: String? = null,
    val tags: Tags,
    val user: User,
    val status: Status,
) {
    init {
        require(title.isNotBlank()) { "제목이 비어있습니다." }
    }
}
