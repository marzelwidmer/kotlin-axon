package ch.keepcalm.kotlinevents

import ch.keepcalm.kotlinevents.order.commandmodel.OrderAggregate
import ch.keepcalm.kotlinevents.order.core.command.ConfirmOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.PlaceOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.ShipOrderCommand
import ch.keepcalm.kotlinevents.order.core.event.OrderConfirmedEvent
import ch.keepcalm.kotlinevents.order.core.event.OrderPlacedEvent
import ch.keepcalm.kotlinevents.order.core.event.OrderShippedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class OrderAggregateUnitTest {

    private lateinit var fixture: FixtureConfiguration<OrderAggregate>

    @Before
    fun setUp() {
        fixture = AggregateTestFixture(OrderAggregate::class.java)
    }

    /**
     * The first test case should cover the simplest situation.
     * When the aggregate handles the PlaceOrderCommand, it should produce an OrderPlacedEvent:
     */
    @Test
    fun `given NoPriorActivity when PlaceOrderCommand then should publish OrderPlacedEvent`() {
        val orderId = UUID.randomUUID().toString()
        val product = "Deluxe Chair"
        fixture.givenNoPriorActivity()
                .`when`(PlaceOrderCommand(orderId, product))
                .expectEvents(OrderPlacedEvent(orderId, product))
    }

    /**
     * Next, we can test the decision-making logic of only being able to ship an Order
     * if it’s been confirmed.
     */
    @Test
    fun `given OrderPlacedEvent when ConfirmOrderCommand then should publish OrderConfirmedEvent`() {
        val orderId = UUID.randomUUID().toString()
        val product = "Deluxe Chair"
        fixture.given(OrderPlacedEvent(orderId, product))
                .`when`(ConfirmOrderCommand(orderId))
                .expectEvents(OrderConfirmedEvent(orderId))
    }

    @Test
    fun `given OrderPlacedEvent when ShipOrderCommand then should ThrowIllegalStateException`() {
        val orderId = UUID.randomUUID().toString()
        val product = "Deluxe Chair"
        fixture.given(OrderPlacedEvent(orderId, product))
                .`when`(ShipOrderCommand(orderId))
                .expectException(IllegalStateException::class.java)
    }

    @Test
    fun `given OrderPlacedEvent and OrderConfirmedEvent when ShipOrderCommand then should publish OrderShippedEvent`() {
        val orderId = UUID.randomUUID().toString()
        val product = "Deluxe Chair"
        fixture.given(OrderPlacedEvent(orderId, product), OrderConfirmedEvent(orderId))
                .`when`(ShipOrderCommand(orderId))
                .expectEvents(OrderShippedEvent(orderId))
    }

}
