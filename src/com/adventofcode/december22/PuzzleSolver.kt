package com.adventofcode.december22

import com.adventofcode.PuzzleSolverAbstract
import com.adventofcode.mylambdas.splitByCondition

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    override fun resultPartOne(): String {
        val playerList = input.inputLines.splitByCondition { it.isEmpty() }.map { Player(it) }

        val winner = playGame(playerList)

        return playerList[winner].cards
            .mapIndexed { index, crd -> (playerList[winner].cards.size - index) * crd}
            .sum()
            .toString()
    }

    override fun resultPartTwo(): String {
        val playerList = input.inputLines.splitByCondition { it.isEmpty() }.map { Player(it) }

        val winner = recursiveCombat(playerList, 1)

        return playerList[winner].cards
            .mapIndexed { index, crd -> (playerList[winner].cards.size - index) * crd}
            .sum()
            .toString()
    }

    private fun playGame(playerList: List<Player>): Int {
        while (playerList.none { pl -> pl.cards.isEmpty() }) {
            val winner = if (playerList[0].cards.first() > playerList[1].cards.first()) 0 else 1
            playerList[winner].cards.add(playerList[winner].cards.first())
            playerList[winner].cards.add(playerList[1-winner].cards.first())
            playerList.forEach { pl -> pl.cards.removeAt(0) }
        }
        return if (playerList[0].cards.isNotEmpty()) 0 else 1
    }

    private fun recursiveCombat(playerList: List<Player>, gameNr: Int): Int {
        val historySet = mutableSetOf<String>()
        while (playerList.none { pl -> pl.cards.isEmpty() }) {
            val hashValue = playerList[0].hashString() + playerList[1].hashString()
            if (hashValue in historySet) {
                return 0
            }
            historySet.add(hashValue)

            val cards = playerList.map{it.cards.removeAt(0) }
            val winner = if (playerList[0].cards.size >= cards[0] && playerList[1].cards.size >= cards[1]) {
                recursiveCombat(playerList.mapIndexed{index, pl -> Player(pl.cards.subList(0,cards[index]))}, gameNr+1)
            } else {
                if (cards[0] > cards[1]) 0 else 1
            }

            playerList[winner].cards.add(cards[winner])
            playerList[winner].cards.add(cards[1-winner])
        }
        return if (playerList[0].cards.isNotEmpty()) 0 else 1
    }
}

class Player(inputCards: MutableList<Int>) {
    val cards = inputCards.map {it}.toMutableList()
    constructor(inputLines: List<String>) : this(inputLines.drop(1).map { it.toInt() }.toMutableList())

    fun hashString(): String {
        return cards.toString()
    }
}
