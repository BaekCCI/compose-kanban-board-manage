package woowacourse.kanban.board.domain

import kotlin.test.Test
import kotlin.test.assertFails
import woowacourse.kanban.board.domain.model.Status

class StatusTest {

    @Test
    fun `To-Do에서 담당자가 있을 경우 In-Progress 전이가 가능하다`() {
        Status.validChangeStatus(Status.TODO, Status.IN_PROGRESS, true)
    }

    @Test
    fun `To-Do에서 담당자가 없는 상태로 상태 전이 시 에러가 발생한다`() {
        assertFails {
            Status.validChangeStatus(Status.TODO, Status.IN_PROGRESS, false)
        }
    }

    @Test
    fun `In-Progress에서 to-do 상태로 전이가 가능하다`() {
        Status.validChangeStatus(Status.IN_PROGRESS, Status.TODO, true)
    }

    @Test
    fun `In-Progress에서 Review 상태로 전이가 가능하다`() {
        Status.validChangeStatus(Status.IN_PROGRESS, Status.REVIEW, true)
    }

    @Test
    fun `In-Progress에서 Done 상태로 전이 시 에러가 발생한다`() {
        assertFails {
            Status.validChangeStatus(Status.IN_PROGRESS, Status.DONE, true)
        }
    }

    @Test
    fun `Review에서 In-Progress로 전이가 가능하다`() {
        Status.validChangeStatus(Status.REVIEW, Status.IN_PROGRESS, true)
    }

    @Test
    fun `Review에서 Done 상태로 전이가 가능하다`() {
        Status.validChangeStatus(Status.REVIEW, Status.DONE, true)
    }

    @Test
    fun `Review에서 to-do 상태로 전이 시 에러가 발생한다`() {
        assertFails {
            Status.validChangeStatus(Status.REVIEW, Status.TODO, true)
        }
    }

    @Test
    fun `Done에서 TODO 상태로 전이가 가능하다`() {
        Status.validChangeStatus(Status.DONE, Status.TODO, true)
    }
}
