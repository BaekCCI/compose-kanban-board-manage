package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.Status
import woowacourse.kanban.board.domain.model.Task

class TaskBoardState {
    var tasks: List<Task> by mutableStateOf(emptyList())

    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()

    fun createTask(task: Task) {
        tasks = tasks + task
    }
}
