package woowacourse.kanban.board.domain.model

enum class Status(val requiredAssignee: Boolean, val canDeleteTask: Boolean) {
    TODO(false, true),
    IN_PROGRESS(true, true),
    REVIEW(true, false),
    DONE(true, false),
    ;

    val movableTo: List<Status>
        get() = when (this) {
            TODO -> listOf(TODO, IN_PROGRESS)
            IN_PROGRESS -> listOf(TODO, REVIEW)
            REVIEW -> listOf(IN_PROGRESS, DONE)
            DONE -> listOf(TODO)
        }

    companion object {
        fun validChangeStatus(from: Status, to: Status, hasAssignee: Boolean) {
            require(to in from.movableTo) { "해당 상태로 옮길 수 없습니다." }
            if (to.requiredAssignee) require(hasAssignee) { "담당자를 지정해야 상태를 옮길 수 있습니다." }
        }
    }
}
