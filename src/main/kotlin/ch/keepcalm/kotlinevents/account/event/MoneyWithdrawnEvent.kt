package ch.keepcalm.kotlinevents.account.event

//   _____                 _     __  __
//  | ____|_   _____ _ __ | |_  |  \/  | ___  _ __   ___ _   _
//  |  _| \ \ / / _ \ '_ \| __| | |\/| |/ _ \| '_ \ / _ \ | | |
//  | |___ \ V /  __/ | | | |_  | |  | | (_) | | | |  __/ |_| |
//  |_____| \_/ \___|_| |_|\__| |_|  |_|\___/|_| |_|\___|\__, |
//                                                       |___/ class MoneyDepositedEvent(id: String, val amount: Double) : BaseEvent<String>(id)
class MoneyWithdrawnEvent(id: String, val amount: Double) : BaseEvent<String>(id)
