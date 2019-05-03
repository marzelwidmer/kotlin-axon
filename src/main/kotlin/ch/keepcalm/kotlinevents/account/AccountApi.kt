package ch.keepcalm.kotlinevents.account

import ch.keepcalm.kotlinevents.account.command.CloseAccountCommand
import ch.keepcalm.kotlinevents.account.command.CreateAccountCommand
import ch.keepcalm.kotlinevents.account.command.DepositMoneyCommand
import ch.keepcalm.kotlinevents.account.command.WithdrawMoneyCommand
import io.swagger.annotations.Api
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.modelling.command.AggregateNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.streams.toList

//https://www.novatec-gmbh.de/en/blog/event-sourcing-spring-boot-axon/

//      _    ____ ___
//     / \  |  _ \_ _|
//    / _ \ | |_) | |
//   / ___ \|  __/| |
//  /_/   \_\_|  |___|
//
@RequestMapping("/accounts")
@RestController
@Api(value="account", description="Operations pertaining to account")
class AccountApi(private val commandGateway: CommandGateway, private val eventStore: EventStore) {

    @GetMapping("{id}/events")
    fun getEvents(@PathVariable id: String): List<Any> {
        return eventStore.readEvents(id).asStream().map<Any> {
            s -> s.payload }.toList()
        //.collect<List<Any>, Any>(Collectors.toList())
    }

    @PostMapping
    fun createAccount(@RequestBody user: AccountOwner): CompletableFuture<String> {
        val id = UUID.randomUUID().toString()
        return commandGateway.send(CreateAccountCommand(id, user.name))
    }

    @PutMapping(path = ["{accountId}/balance"])
    fun deposit(@RequestBody ammount: Double, @PathVariable accountId: String): CompletableFuture<String> {
        return if (ammount > 0) {
            commandGateway.send(DepositMoneyCommand(accountId, ammount))
        } else {
            commandGateway.send(WithdrawMoneyCommand(accountId, -ammount))
        }
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): CompletableFuture<String> {
        return commandGateway.send(CloseAccountCommand(id))
    }

    @ExceptionHandler(AggregateNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound() {
    }

    @ExceptionHandler(BankAccount.InsufficientBalanceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun insufficientBalance(exception: BankAccount.InsufficientBalanceException): String? {
        return exception.message
    }
}
data class AccountOwner(val name: String)

