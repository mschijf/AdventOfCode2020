package com.adventofcode.december01

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    override fun resultPartOne(): String {
        val list = input.inputLines.map{it.toInt()}
        for (i in 0 until list.size-1)
            for (j in i+1 until list.size)
                if (list[i] + list[j] == 2020) {
                    return (list[i] * list[j]).toString()
                }
        return "Not found"
    }

    override fun resultPartTwo(): String {
        val list = input.inputLines.map{it.toInt()}
        for (i in 0 until list.size-2)
            for (j in i+1 until list.size-1)
                for (k in j+1 until list.size)
                    if (list[i] + list[j] + list[k] == 2020) {
                        return (list[i] * list[j] * list[k]).toString()
                    }
        return "Not found"
    }
}


