package ch.keepcalm.kotlinevents.order.command

import ch.keepcalm.kotlinevents.order.event.OrderPlacedEvent
import ch.keepcalm.kotlinevents.order.event.OrderConfirmedEvent
import ch.keepcalm.kotlinevents.order.event.OrderShippedEvent

import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateLifecycle.apply  // TODO axon apply
import org.springframework.util.Assert

// https://www.baeldung.com/axon-cqrs-event-sourcing
@Aggregate
class OrderAggregate {

    @AggregateIdentifier
    private var orderId: String? = null
    private var orderConfirmed: Boolean = false

    @CommandHandler
    constructor(command: PlaceOrderCommand) {
        AggregateLifecycle.apply(OrderPlacedEvent(command.orderId, command.product))
    }

    @EventSourcingHandler
    fun on(event: OrderPlacedEvent) {
        this.orderId = event.orderId
        orderConfirmed = false
    }
    protected constructor() {}



    @CommandHandler
    fun handle(command: ConfirmOrderCommand) {
        Assert.hasLength(command.orderId, "Missing id")
        apply(OrderConfirmedEvent(command.orderId))
    }

    @CommandHandler
    fun handle(command: ShipOrderCommand) {
        if (!orderConfirmed) {
            throw IllegalStateException("Cannot ship an order which has not been confirmed yet.")
        }
        apply(OrderShippedEvent(orderId))
    }

    @EventSourcingHandler
    fun on(event: OrderConfirmedEvent) {
        orderConfirmed = true
    }
}