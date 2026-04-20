package com.tiorico.app.game

import com.tiorico.app.utils.Constants

object GameLogic {
    fun applyAction(money: Int, action: String): Int = when (action) {
        "SAVE"   -> money + Constants.SAVE_GAIN
        "INVEST" -> if ((0..1).random() == 1) money + Constants.INVEST_WIN else money + Constants.INVEST_LOSS
        "SPEND"  -> money - Constants.SPEND_LOSS
        else     -> money
    }

    fun applyRandomEvent(money: Int): Pair<Int, String> = when ((0..4).random()) {
        0 -> Pair(money + 200, "Evento: Encontraste dinero! +\$200")
        1 -> Pair(money - 150, "Evento: Multa de transito -\$150")
        2 -> Pair(money + 100, "Evento: Bono extra +\$100")
        3 -> Pair(money - 100, "Evento: Reparacion urgente -\$100")
        else -> Pair(money, "Sin evento esta ronda")
    }

    fun isAlive(money: Int) = money > 0
    fun hasWon(round: Int, money: Int) = round > Constants.TOTAL_ROUNDS && isAlive(money)
    fun hasLost(money: Int) = money <= 0
}


