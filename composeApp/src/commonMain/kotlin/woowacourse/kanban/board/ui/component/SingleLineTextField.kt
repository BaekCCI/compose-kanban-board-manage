package woowacourse.kanban.board.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.ui.theme.Gray
import woowacourse.kanban.board.ui.theme.Gray500
import woowacourse.kanban.board.ui.theme.Gray800
import woowacourse.kanban.board.ui.theme.Red

@Composable
fun SingleLineTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    placeHolder: String,
    supportingText: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        placeholder = {
            Text(
                text = placeHolder,
                color = Gray,
            )
        },
        supportingText = {
            supportingText?.let {
                Text(
                    text = it,
                    color = if (isError) Red else Gray800,
                )
            }
        },
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "error",
                    tint = Red,
                )
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            unfocusedBorderColor = Gray500,
            errorBorderColor = Red,
        ),
    )
}

@Composable
@Preview(showBackground = true)
private fun SingLineTextFieldPreview() {
    SingleLineTextField(
        placeHolder = "제목을 입력해주세요.",
        isError = false,
        supportingText = "제목을 입력해주세요.",
        value = "",
        onValueChange = { print(it) },
    )
}
