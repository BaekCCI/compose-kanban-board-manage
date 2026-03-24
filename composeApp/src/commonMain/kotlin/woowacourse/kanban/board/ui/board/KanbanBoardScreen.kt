package woowacourse.kanban.board.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.snackbar_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_error_create_new_task
import kanbanboard.composeapp.generated.resources.snackbar_unknown_error
import org.jetbrains.compose.resources.getString
import woowacourse.kanban.board.domain.TaskCreator
import woowacourse.kanban.board.ui.dialog.TaskCreateDialog
import woowacourse.kanban.board.ui.util.SnackBarEvent

@Composable
fun KanbanBoardScreen() {
    val boardState = remember { TaskBoardState() }
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
        TaskBoard(
            uiState = boardState,
            onClickCreate = { showDialog = true },
        )
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
@Preview
private fun KanbanBoardScreenPreview() {
    KanbanBoardScreen()
}
