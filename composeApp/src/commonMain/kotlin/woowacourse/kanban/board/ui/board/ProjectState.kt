package woowacourse.kanban.board.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.domain.model.Task

class ProjectState(val projects: List<KanbanProject>, initialProject: KanbanProject) {

    var selectedProject: KanbanProject by mutableStateOf(initialProject)

    fun selectProject(project: KanbanProject) {
        selectedProject = project
    }
}
