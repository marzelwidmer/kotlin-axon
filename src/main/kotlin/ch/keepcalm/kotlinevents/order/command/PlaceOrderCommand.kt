package ch.keepcalm.kotlinevents.order.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.validation.constraints.NotNull

data class PlaceOrderCommand(@TargetAggregateIdentifier @NotNull(message = "Id cannot be null") val orderId: String, val product: String)
