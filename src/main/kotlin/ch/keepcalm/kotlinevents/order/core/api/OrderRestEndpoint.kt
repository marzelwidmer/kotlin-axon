package ch.keepcalm.kotlinevents.order.core.api

import ch.keepcalm.kotlinevents.order.core.command.ConfirmOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.PlaceOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.ShipOrderCommand
import ch.keepcalm.kotlinevents.order.core.query.FindAllOrderedProductsQuery
import ch.keepcalm.kotlinevents.order.core.query.OrderedProduct
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class OrderRestEndpoint(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {


    @PostMapping(value = ["/ship-order"])
    fun shipOrder() {
        val orderId = UUID.randomUUID().toString()
        commandGateway.send<PlaceOrderCommand>(PlaceOrderCommand(orderId = orderId, product = "Deluxe Chair"))
        commandGateway.send<ConfirmOrderCommand>(ConfirmOrderCommand(orderId = orderId))
        commandGateway.send<ShipOrderCommand>(ShipOrderCommand(orderId = orderId))
    }

    @PostMapping(value = ["/ship-unconfirmed-order"])
    fun shipUnconfirmedOrder() {
        val orderId = UUID.randomUUID().toString()
        commandGateway.send<Any>(PlaceOrderCommand(orderId, "Deluxe Chair"))
        // This throws an exception, as an Order cannot be shipped if it has not been confirmed yet.
        commandGateway.send<Any>(ShipOrderCommand(orderId))
    }

    @GetMapping(value = ["/all-orders"])
    fun findAllOrderedProducts(): List<OrderedProduct> {
        return queryGateway.query(FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf<OrderedProduct>(OrderedProduct::class.java)).join()
    }
}