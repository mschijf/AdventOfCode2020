package com.adventofcode.december06

import com.adventofcode.PuzzleSolverAbstract
import com.adventofcode.mylambdas.splitByCondition

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {
    private val groupList = input.inputLines.splitByCondition { it.isEmpty() }.map { Group(it)}

    override fun resultPartOne(): String {
        return groupList.sumOf { it.distinctAnswers().count() }.toString()
    }

    override fun resultPartTwo(): String {
        return groupList.sumOf { it.sameAnswers().count() }.toString()
    }

}


class Group(
    private val answerList: List<String>) {

    fun distinctAnswers() = answerList.map {it.toSet()}.flatten().toSet()
    fun sameAnswers() = answerList.map {it.toSet()}.reduce {acc, next -> acc.intersect(next) }
}
