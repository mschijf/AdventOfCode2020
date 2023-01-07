package com.adventofcode.december05

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    private val boardingpassList = input.inputLines
        .map {
            Pair(
                it.substring(0,7)
                    .replace('F', '0').replace('B', '1')
                    .toInt(2),
                it.substring(7)
                    .replace('L', '0').replace('R', '1')
                    .toInt(2))
        }
        .map {8*it.first + it.second}

    override fun resultPartOne(): String {
        return boardingpassList.max().toString()
    }

    override fun resultPartTwo(): String {
        val sortedBoardingPass = boardingpassList.sorted()
        for (i in 1 until sortedBoardingPass.size-1) {
            if (sortedBoardingPass[i-1] == sortedBoardingPass[i] - 2)
                return (sortedBoardingPass[i] - 1).toString()
        }
        return "NOT FOUND"
    }
}

