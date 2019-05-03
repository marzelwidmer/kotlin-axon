package ch.keepcalm.kotlinevents.account.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.validation.constraints.NotNull


//   ____                  ____                                          _
//  | __ )  __ _ ___  ___ / ___|___  _ __ ___  _ __ ___   __ _ _ __   __| |
//  |  _ \ / _` / __|/ _ \ |   / _ \| '_ ` _ \| '_ ` _ \ / _` | '_ \ / _` |
//  | |_) | (_| \__ \  __/ |___ (_) | | | | | | | | | | | (_| | | | | (_| |
//  |____/ \__,_|___/\___|\____\___/|_| |_| |_|_| |_| |_|\__,_|_| |_|\__,_|
//
open class BaseCommand<T>(@field:TargetAggregateIdentifier @NotNull(message = "Id cannot be null") val id: String)