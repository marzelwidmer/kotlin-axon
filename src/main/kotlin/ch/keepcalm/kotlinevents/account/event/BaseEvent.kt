package ch.keepcalm.kotlinevents.account.event

import javax.validation.constraints.NotNull

//   ____                 _____                 _
//  | __ )  __ _ ___  ___| ____|_   _____ _ __ | |_
//  |  _ \ / _` / __|/ _ \  _| \ \ / / _ \ '_ \| __|
//  | |_) | (_| \__ \  __/ |___ \ V /  __/ | | | |_
//  |____/ \__,_|___/\___|_____| \_/ \___|_| |_|\__|
//
//
open class BaseEvent<T>(@NotNull(message = "Id cannot be null") val id: T)