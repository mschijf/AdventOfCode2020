package com.adventofcode.december03

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=true).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    private val forest = input.inputLines

    override fun resultPartOne(): String {
        return countTrees(Pair(1,3)).toString()
    }

    override fun resultPartTwo(): String {
        var result = 1L
        val slopeList = listOf(Pair(1,1), Pair(1,3), Pair(1,5), Pair(1,7), Pair(2,1))
        for (slope in slopeList) {
            result *= countTrees(slope)
        }
        return result.toString()
    }

    private fun countTrees(slope: Pair<Int, Int>): Int {
        var x = 0
        var y = 0
        var countTree = 0
        while (y < forest.size) {
            if (forest[y][x % forest[y].length] == '#')
                countTree++
            x += slope.second
            y += slope.first
        }
        return countTree
    }
}


