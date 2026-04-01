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
        fun canChangeStatus(from: Status, to: Status, hasAssignee: Boolean): Boolean {
            return when (from) {
                TODO -> {
                    if (!hasAssignee) false else to in from.movableTo
                }

                else -> to in from.movableTo
            }
        }
    }
}
