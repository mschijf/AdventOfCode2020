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
        val squareArrangement = SquareArrangement(tileList, false)
        val x = squareArrangement.getArrangement()
        return x.toString()
    }

    override fun resultPartTwo(): String {
        val squareArrangement = SquareArrangement(tileList, true)
        val countMonster = squareArrangement.getArrangement()
        return countMonster.toString()
    }

}