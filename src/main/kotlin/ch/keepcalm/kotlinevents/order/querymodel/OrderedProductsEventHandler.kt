package ch.keepcalm.kotlinevents.order.querymodel

import ch.keepcalm.kotlinevents.order.core.event.OrderPlacedEvent
import ch.keepcalm.kotlinevents.order.core.query.FindAllOrderedProductsQuery
import ch.keepcalm.kotlinevents.order.core.query.OrderedProduct
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service
import java.util.HashMap


@Service
class OrderedProductsEventHandler {

    private val orderedProducts = HashMap<String, OrderedProduct>()

    @EventHandler
    fun on(event: OrderPlacedEvent) {
        val orderId = event.orderId
        orderedProducts[orderId] = OrderedProduct(orderId, event.product)
    }

    @QueryHandler
    fun handle(query: FindAllOrderedProductsQuery): List<OrderedProduct> {
        return ArrayList(orderedProducts.values)
    }
}