package ch.keepcalm.kotlinevents.order.core.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.validation.constraints.NotNull

data class ShipOrderCommand(@TargetAggregateIdentifier @NotNull(message = "Id cannot be null") val orderId: String)