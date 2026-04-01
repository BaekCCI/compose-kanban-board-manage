package woowacourse.kanban.board.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class KanbanProject(val id: String = Uuid.random().toString(), val name: String, val tasks: List<Task> = emptyList()) {
    val totalCount: Int get() = tasks.size
    val completeCount: Int get() = tasks.count { it.status == Status.DONE }
    val completeRatio: Float get() = if (totalCount == 0) 0f else completeCount.toFloat() / totalCount.toFloat()

    fun addTask(newTask: Task): KanbanProject {
        return this.copy(tasks = tasks + newTask)
    }

    fun updateStatus(taskId: String, newStatus: Status): KanbanProject {
        require(tasks.any { it.id == taskId }) { "$taskId 태스크를 찾을 수 없습니다." }
        val updatedTasks = tasks.map { if (it.id == taskId) it.copy(status = newStatus) else it }

        return this.copy(tasks = updatedTasks)
    }
}
