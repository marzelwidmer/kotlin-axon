package ch.keepcalm.kotlinevents.account.command

//    ____                                          _   __  __
//   / ___|___  _ __ ___  _ __ ___   __ _ _ __   __| | |  \/  | ___  _ __   ___ _   _
//  | |   / _ \| '_ ` _ \| '_ ` _ \ / _` | '_ \ / _` | | |\/| |/ _ \| '_ \ / _ \ | | |
//  | |___ (_) | | | | | | | | | | | (_| | | | | (_| | | |  | | (_) | | | |  __/ |_| |
//   \____\___/|_| |_| |_|_| |_| |_|\__,_|_| |_|\__,_| |_|  |_|\___/|_| |_|\___|\__, |
//                                                                              |___/

class WithdrawMoneyCommand(id: String, val amount: Double) : BaseCommand<String>(id)


