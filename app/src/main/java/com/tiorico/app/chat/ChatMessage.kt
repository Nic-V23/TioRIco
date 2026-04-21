package com.tiorico.app.chat



data class ChatMessage(

    val uid: String = "",

    val senderName: String = "",

    val message: String = "",

    val timestamp: Long = 0L

)