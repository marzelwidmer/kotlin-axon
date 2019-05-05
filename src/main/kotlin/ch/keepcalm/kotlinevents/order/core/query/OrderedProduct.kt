package ch.keepcalm.kotlinevents.order.core.query

data class OrderedProduct(val orderId: String, val product: String,
                          var orderStatus: OrderStatus = OrderStatus.PLACED) {
    fun setOrderConfirmed() {
        this.orderStatus = OrderStatus.CONFIRMED
    }

    fun setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED
    }
}

enum class OrderStatus {
    PLACED, CONFIRMED, SHIPPED
}