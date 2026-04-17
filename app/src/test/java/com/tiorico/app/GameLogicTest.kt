package com.tiorico.app



import com.tiorico.app.game.GameLogic

import com.tiorico.app.utils.Constants

import org.junit.Assert.*

import org.junit.Test



class GameLogicTest {



    @Test

    fun saveIncreasesMoneyByFixedAmount() {

        val result = GameLogic.applyAction(1000, "SAVE")

        assertEquals(1000 + Constants.SAVE_GAIN, result)

    }



    @Test

    fun spendDecreasesMoneyByFixedAmount() {

        val result = GameLogic.applyAction(1000, "SPEND")

        assertEquals(1000 - Constants.SPEND_LOSS, result)

    }



    @Test

    fun investChangesMoneyByExpectedRange() {

        val results = (1..100).map { GameLogic.applyAction(1000, "INVEST") }.toSet()

        val expected = setOf(1000 + Constants.INVEST_WIN, 1000 + Constants.INVEST_LOSS)

        assertTrue("invest debe retornar uno de dos valores", results.any { it in expected })

    }



    @Test

    fun playerIsAliveWithPositiveMoney() = assertTrue(GameLogic.isAlive(500))



    @Test

    fun playerIsDeadWithZeroMoney() = assertFalse(GameLogic.isAlive(0))



    @Test

    fun playerIsDeadWithNegativeMoney() = assertFalse(GameLogic.isAlive(-1))



    @Test

    fun playerWinsAfterAllRoundsWithPositiveMoney() =

        assertTrue(GameLogic.hasWon(Constants.TOTAL_ROUNDS + 1, 500))



    @Test

    fun playerDoesNotWinBeforeLastRound() =

        assertFalse(GameLogic.hasWon(5, 500))



    @Test

    fun playerDoesNotWinWithZeroMoney() =

        assertFalse(GameLogic.hasWon(Constants.TOTAL_ROUNDS + 1, 0))



    @Test

    fun randomEventReturnsPair() {

        val (money, msg) = GameLogic.applyRandomEvent(1000)

        assertTrue(money is Int)

        assertTrue(msg.isNotEmpty())

    }

}