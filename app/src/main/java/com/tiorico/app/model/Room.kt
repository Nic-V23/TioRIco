package com.tiorico.app.model



data class Room(

    val roomCode: String = "",

    val hostUid: String = "",

    val players: Map<String, Player> = emptyMap(),

    val started: Boolean = false,

    val currentRound: Int = 1

)