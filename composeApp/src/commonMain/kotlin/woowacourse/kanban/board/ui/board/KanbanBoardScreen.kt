package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_error_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_unknown_error
import org.jetbrains.compose.resources.getString
import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.domain.model.KanbanProject
import woowacourse.kanban.board.ui.dialog.TaskCreateDialog
import woowacourse.kanban.board.ui.util.SnackBarEvent

@Composable
fun KanbanBoardScreen(initialProjectState: ProjectState) {
    val boardState = remember { TaskBoardState() }
    val projectState = remember { initialProjectState }
    var showDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    var snackBarEvent: SnackBarEvent? by remember { mutableStateOf(null) }

    LaunchedEffect(snackBarEvent?.id) {
        snackBarEvent?.let {
            snackBarHostState.showSnackbar(
                message = when {
                    it.message != null -> it.message
                    it.strRes != null -> getString(it.strRes)
                    else -> getString(Res.string.snackbar_unknown_error)
                },
                withDismissAction = true,
            )
        }
        snackBarEvent = null
    }

    Box {
        if (showDialog) {
            TaskCreateDialog(
                onDismissRequest = { showDialog = false },
                onConfirm = { title, description, tags, status, assignee ->
                    val result =
                        TaskCreator.create(title = title, description = description, tags = tags, assignee = assignee, status = status)

                    result.onSuccess { newTask ->
                        boardState.createTask(newTask)
                        showDialog = false
                        snackBarEvent =
                            SnackBarEvent(
                                strRes = Res.string.snackbar_create_new_task,
                            )
                    }.onFailure { exception ->
                        snackBarEvent = SnackBarEvent(
                            strRes = Res.string.snackbar_error_create_new_task,
                            message = exception.message,
                        )
                    }
                },
            )
        }
        Row {
            ProjectSideBar(
                projects = projectState.projects,
                selectedProject = projectState.selectedProject,
                onProjectSelect = { projectState.selectProject(it) },
                modifier = Modifier.width(255.dp).fillMaxHeight(),
            )
            VerticalDivider(modifier = Modifier.width(1.dp).background(Color(0xffE5E7EB)))
            TaskBoard(
                uiState = boardState,
                project = projectState.selectedProject,
                onClickCreate = { showDialog = true },
                modifier = Modifier.semantics { contentDescription = "${projectState.selectedProject.name} 화면" },
            )
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
@Preview
private fun KanbanBoardScreenPreview() {
    KanbanBoardScreen(initialProjectState = ProjectState(listOf(), KanbanProject("A프로젝트")))
}
