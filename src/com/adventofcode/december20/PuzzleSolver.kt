package com.adventofcode.december20

import com.adventofcode.PuzzleSolverAbstract
import com.adventofcode.mylambdas.splitByCondition

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    private val tileList = input.inputLines.splitByCondition { it.isEmpty() }.map { Tile(it) }
    init {
        tileList.forEach {it.updateMatchList(tileList)}
    }

    override fun resultPartOne(): String {
        val squareArrangement = SquareArrangement(tileList)
        val arrangement = squareArrangement.getFirstArrangement()
        return arrangement.getCornerSquaresProduct().toString()
    }

    override fun resultPartTwo(): String {
        val squareArrangement = SquareArrangement(tileList)
        val arrangement = squareArrangement.getArrangementWithSeaMonsters()
        return arrangement.getRoughness().toString()
    }

}