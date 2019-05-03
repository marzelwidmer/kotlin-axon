package ch.keepcalm.kotlinevents.account

import ch.keepcalm.kotlinevents.account.command.CloseAccountCommand
import ch.keepcalm.kotlinevents.account.command.CreateAccountCommand
import ch.keepcalm.kotlinevents.account.command.DepositMoneyCommand
import ch.keepcalm.kotlinevents.account.command.WithdrawMoneyCommand
import ch.keepcalm.kotlinevents.account.event.AccountClosedEvent
import ch.keepcalm.kotlinevents.account.event.AccountCreatedEvent
import ch.keepcalm.kotlinevents.account.event.MoneyDepositedEvent
import ch.keepcalm.kotlinevents.account.event.MoneyWithdrawnEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.util.Assert
import java.io.Serializable


//      _                                    _
//     / \   __ _  __ _ _ __ ___  __ _  __ _| |_ ___
//    / _ \ / _` |/ _` | '__/ _ \/ _` |/ _` | __/ _ \
//   / ___ \ (_| | (_| | | |  __/ (_| | (_| | |_  __/
//  /_/   \_\__, |\__, |_|  \___|\__, |\__,_|\__\___|
//          |___/ |___/          |___/
//

@Aggregate
class BankAccount : Serializable {

    @AggregateIdentifier
    private var id: String? = null

    final var balance: Double = 0.toDouble()
        private set

    private var owner: String? = null

    @CommandHandler
    constructor(command: CreateAccountCommand) {
        val id = command.id
        val name = command.accountCreator

        Assert.hasLength(id, "Missing id")
        Assert.hasLength(name, "Missig account creator")

        AggregateLifecycle.apply(AccountCreatedEvent(id, name, 0.0))
    }

    @EventSourcingHandler
    protected fun on(event: AccountCreatedEvent) {
        this.id = event.id
        this.owner = event.accountCreator
        this.balance = event.balance
    }

    @CommandHandler
    protected fun on(command: CloseAccountCommand) {
        AggregateLifecycle.apply(id?.let { AccountClosedEvent(it) })
    }

    @EventSourcingHandler
    protected fun on(event: AccountClosedEvent) {
        AggregateLifecycle.markDeleted()
    }

    @CommandHandler
    protected fun on(command: DepositMoneyCommand) {
        val ammount = command.ammount

        Assert.isTrue(ammount > 0.0, "Deposit must be a positiv number.")

        AggregateLifecycle.apply(id?.let { MoneyDepositedEvent(it, ammount) })
    }


    @EventSourcingHandler
    protected fun on(event: MoneyDepositedEvent) {
        this.balance += event.amount
    }

    @CommandHandler
    protected fun on(command: WithdrawMoneyCommand) {
        val amount = command.amount

        Assert.isTrue(amount > 0.0, "Withdraw must be a positiv number.")

        if (balance - amount < 0) {
            throw InsufficientBalanceException("Insufficient balance. Trying to withdraw: $amount, but current balance is: $balance")
        }

        AggregateLifecycle.apply(id?.let { MoneyWithdrawnEvent(it, amount) })
    }

    class InsufficientBalanceException internal constructor(message: String) : RuntimeException(message)

    @EventSourcingHandler
    protected fun on(event: MoneyWithdrawnEvent) {
        this.balance -= event.amount
    }
}