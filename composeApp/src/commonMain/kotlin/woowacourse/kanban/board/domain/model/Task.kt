package woowacourse.kanban.board.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
data class Task(
    val id: String = Uuid.random().toString(),
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
