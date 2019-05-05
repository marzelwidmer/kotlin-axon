package ch.keepcalm.kotlinevents.order.core.api

import org.axonframework.queryhandling.QueryGateway
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.RestController
import ch.keepcalm.kotlinevents.order.core.command.ShipOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.ConfirmOrderCommand
import ch.keepcalm.kotlinevents.order.core.command.PlaceOrderCommand
import java.util.UUID
import org.springframework.web.bind.annotation.PostMapping


@RestController
class OrderRestEndpoint(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {


    @PostMapping(value = ["/ship-order"])
    fun shipOrder() {
        val orderId = UUID.randomUUID().toString()
        commandGateway.send<PlaceOrderCommand>(PlaceOrderCommand(orderId = orderId, product = "Deluxe Chair"))
        commandGateway.send<ConfirmOrderCommand>(ConfirmOrderCommand(orderId = orderId))
        commandGateway.send<ShipOrderCommand>(ShipOrderCommand(orderId = orderId))
    }
}