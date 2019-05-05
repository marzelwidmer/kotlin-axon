package ch.keepcalm.kotlinevents.order.event

data class OrderPlacedEvent(val orderId: String, private val product: String)
