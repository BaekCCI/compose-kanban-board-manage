package woowacourse.kanban.board.study

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

@OptIn(ExperimentalTestApi::class)
class ComposeTestRule {

//    @Test
//    fun `동기화`() = runComposeUiTest {
//        var counter by mutableStateOf(0)
//        var latestCounter = 0
//
//        setContent {
//            Column {
//                Button(onClick = { counter++ }) {
//                    Text(counter.toString())
//                }
//                latestCounter = counter
//            }
//        }
//        counter = 1
//        assertThat(counter).isEqualTo(latestCounter)
//    }

    @Test
    fun `동기화2`() = runComposeUiTest {
        var counter by mutableStateOf(0)
        var latestCounter = 0

        setContent {
            Column {
                Button(onClick = { counter++ }) {
                    Text(counter.toString())
                }
                latestCounter = counter
            }
        }
        counter = 1
        waitForIdle() // 동기화
        assertThat(counter).isEqualTo(latestCounter)
    }

    @Test
    fun `동기화3`() = runComposeUiTest {
        var counter by mutableStateOf(0)
        var latestCounter = 0

        setContent {
            Column {
                Button(onClick = { counter++ }) {
                    Text(counter.toString())
                }
                latestCounter = counter
            }
        }
        counter = 1
        onNodeWithText("1").assertExists() // 동기화 할 필요 X
    }

    @Test
    fun `노드 병합1`() = runComposeUiTest {
        setContent {
            Button(onClick = {}, modifier = Modifier.testTag("버튼")) {
                Text("확인")
                Text("버튼")
            }
        }
        onNodeWithText("확인").assertExists()
    }

    @Test
    fun `노드 병합2`() = runComposeUiTest {
        setContent {
            Button(onClick = {}, modifier = Modifier.testTag("버튼")) {
                Text("확인", modifier = Modifier.testTag("확인"))
                Text("버튼")
            }
        }
        onNodeWithTag("버튼", useUnmergedTree = true).printToLog("로그")
        onNodeWithTag("확인", useUnmergedTree = true).assertTextEquals("확인")
    }
}
