package ch.keepcalm.kotlinevents.order.core.event

data class OrderPlacedEvent(val orderId: String, val product: String)
