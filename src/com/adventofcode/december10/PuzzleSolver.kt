package com.adventofcode.december10

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    override fun resultPartOne(): String {
        val adapterList = input.inputLines.map {it.toInt()}.sorted()
        val maxAdapter = adapterList.last()+3
        val newList = (listOf(0)+adapterList).zip(adapterList+ listOf(maxAdapter)) { a, b -> b - a}
        return (newList.count {it == 1} * newList.count {it == 3}).toString()
    }

    override fun resultPartTwo(): String {
        val adapterList = input.inputLines.map {it.toInt()}.sorted()
        return solver(adapterList, 0, 0).toString()
    }

    private val cache = hashMapOf<Int, Long>()
    private fun solver(adapterList: List<Int>, fromIndex: Int, lastAdapterValue: Int): Long {
        if (fromIndex >= adapterList.size)
            return 1
        if (cache.contains(fromIndex))
            return cache[fromIndex]!!

        var i = fromIndex
        var sum = 0L
        while (i < adapterList.size && adapterList[i] <= lastAdapterValue+3) {
            val count = solver(adapterList, i+1, adapterList[i])
            sum += count
            i++
        }
        cache[fromIndex] = sum
        return sum
    }

}


