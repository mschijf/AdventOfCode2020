package com.adventofcode.december23

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {


    override fun resultPartOne(): String {
        val cupList =  CupLinkedList(input.inputLines.first())

//        println(cupList)
        repeat(100) { moveNr ->
            cupList.move()
//            println("${moveNr+1} : $cupList")
        }
        return cupList.labelsAfterOne()
    }

    override fun resultPartTwo(): String {
        val cupList =  CupLinkedList(input.inputLines.first(), extraMillion = true)
        repeat(10_000_000) {
            cupList.move()
        }
        return cupList.twoCupsProductAfterCupOne().toString()
    }

}



