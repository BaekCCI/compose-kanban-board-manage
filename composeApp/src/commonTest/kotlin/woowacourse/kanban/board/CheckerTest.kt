package woowacourse.kanban.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.study.CheckerView

@OptIn(ExperimentalTestApi::class)
class CheckerTest {

    @Test
    fun `버튼 클릭 시 체크 상태 노출 여부 확인`() = runComposeUiTest {
        var checked by mutableStateOf(true)

        setContent {
            CheckerView(check = {}, checked = checked)
        }

        onNodeWithText("checked!!").assertExists()
    }
}
