package ch.keepcalm.kotlinevents.account.command


//    ____                                          _      _                             _
//   / ___|___  _ __ ___  _ __ ___   __ _ _ __   __| |    / \   ___ ___ ___  _   _ _ __ | |_
//  | |   / _ \| '_ ` _ \| '_ ` _ \ / _` | '_ \ / _` |   / _ \ / __/ __/ _ \| | | | '_ \| __|
//  | |___ (_) | | | | | | | | | | | (_| | | | | (_| |  / ___ \ (__ (__ (_) | |_| | | | | |_
//   \____\___/|_| |_| |_|_| |_| |_|\__,_|_| |_|\__,_| /_/   \_\___\___\___/ \__,_|_| |_|\__|
//
class CreateAccountCommand(id: String, val accountCreator: String) : BaseCommand<String>(id)
