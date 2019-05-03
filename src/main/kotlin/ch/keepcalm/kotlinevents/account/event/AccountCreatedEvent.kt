package ch.keepcalm.kotlinevents.account.event


//   _____                 _        _                             _
//  | ____|_   _____ _ __ | |_     / \   ___ ___ ___  _   _ _ __ | |_
//  |  _| \ \ / / _ \ '_ \| __|   / _ \ / __/ __/ _ \| | | | '_ \| __|
//  | |___ \ V /  __/ | | | |_   / ___ \ (__ (__ (_) | |_| | | | | |_
//  |_____| \_/ \___|_| |_|\__| /_/   \_\___\___\___/ \__,_|_| |_|\__|
//
class AccountCreatedEvent(id: String, val accountCreator: String, val balance: Double) : BaseEvent<String>(id)