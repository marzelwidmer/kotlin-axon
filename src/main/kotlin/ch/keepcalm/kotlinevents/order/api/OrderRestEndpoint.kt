package ch.keepcalm.kotlinevents.order.api

import ch.keepcalm.kotlinevents.order.core.command.ConfirmOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.PlaceOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.ShipOrderCommand
import ch.keepcalm.kotlinevents.order.core.query.FindAllOrderedProductsQuery
import ch.keepcalm.kotlinevents.order.core.query.OrderedProduct
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.streams.toList


@RestController
class OrderRestEndpoint(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway,
                        private val eventStore: EventStore) {


    @GetMapping("/order/{id}")
    fun getEvents(@PathVariable id: String): List<Any> {
        return eventStore.readEvents(id).asStream().map<Any> {
            s -> s.payload }.toList()
        //.collect<List<Any>, Any>(Collectors.toList())
    }
    @PostMapping(value = ["/ship-order"])
    fun shipOrder(@RequestBody product: String) {
        val orderId = UUID.randomUUID().toString()
        commandGateway.send<PlaceOrderCommand>(PlaceOrderCommand(orderId = orderId, product = product))
        commandGateway.send<ConfirmOrderCommand>(ConfirmOrderCommand(orderId = orderId))
        commandGateway.send<ShipOrderCommand>(ShipOrderCommand(orderId = orderId))
    }

    @PostMapping(value = ["/ship-unconfirmed-order"])
    fun shipUnconfirmedOrder(@RequestBody product: String) {
        val orderId = UUID.randomUUID().toString()
        commandGateway.send<Any>(PlaceOrderCommand(orderId = orderId, product = product))
        // This throws an exception, as an Order cannot be shipped if it has not been confirmed yet.
        commandGateway.send<Any>(ShipOrderCommand(orderId = orderId))
    }

    @GetMapping(value = ["/all-orders"])
    fun findAllOrderedProducts(): List<OrderedProduct> {
        return queryGateway.query(FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf<OrderedProduct>(OrderedProduct::class.java)).join()
    }
}