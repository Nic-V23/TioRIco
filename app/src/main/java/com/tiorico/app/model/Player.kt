package com.tiorico.app.model



data class Player(

    val uid: String = "",

    val name: String = "",

    val money: Int = 1000,

    val currentRound: Int = 1,

    val isAlive: Boolean = true

)