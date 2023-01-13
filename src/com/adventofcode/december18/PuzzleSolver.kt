package com.adventofcode.december18

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    override fun resultPartOne(): String {
        val x = input.inputLines.map { Expr.fromString(it) }
        val y = x.map { it.getValue() }
        return y.sum().toString()

    }

    override fun resultPartTwo(): String {
        val x = input.inputLines.map { Expr.fromStringPartTwo(it) }
        val y = x.map { it.getValue() }
        return y.sum().toString()

    }
}

abstract class Expr{

    abstract fun getValue(): Long

    companion object {
        fun fromStringPartTwo(input: String): Expr {
            val cleanInput = removeBeginEndParenthesis(input)
            if (cleanInput.length == 1) {
                return Operand(cleanInput.toLong())
            }

            val multiplyIndex = findOperator(cleanInput, '*')
            if (multiplyIndex >= 0) {
                val right = cleanInput.substring(multiplyIndex+2, cleanInput.length)
                val left = cleanInput.substring(0, multiplyIndex-1)
                val operator = cleanInput[multiplyIndex]
                return Expression(fromStringPartTwo(left), fromStringPartTwo(right), operator)
            }

            if (cleanInput.last() == ')') {
                val index = findLeftParenthesisFromRight(cleanInput)
                val right = cleanInput.substring(index, cleanInput.length)
                val left = cleanInput.substring(0, index-3)
                val operator = cleanInput[index-2]
                return Expression(fromStringPartTwo(left), fromStringPartTwo(right), operator)
            } else {
                val right = cleanInput.last().toString()
                val left = cleanInput.substring(0, cleanInput.length-4)
                val operator = cleanInput[cleanInput.length-3]
                return Expression(fromStringPartTwo(left), fromStringPartTwo(right), operator)
            }
        }

        fun fromString(input: String): Expr {
            val cleanInput = removeBeginEndParenthesis(input)
            if (cleanInput.length == 1) {
                return Operand(cleanInput.toLong())
            }

            if (cleanInput.last() == ')') {
                val index = findLeftParenthesisFromRight(cleanInput)
                val right = cleanInput.substring(index, cleanInput.length)
                val left = cleanInput.substring(0, index-3)
                val operator = cleanInput[index-2]
                return Expression(fromString(left), fromString(right), operator)
            } else {
                val right = cleanInput.last().toString()
                val left = cleanInput.substring(0, cleanInput.length-4)
                val operator = cleanInput[cleanInput.length-3]
                return Expression(fromString(left), fromString(right), operator)
            }
        }

        private fun removeBeginEndParenthesis(input: String): String {
            return if (input.last() == ')') {
                val index = findLeftParenthesisFromRight(input)
                if (index == 0) {
                    removeBeginEndParenthesis(input.substring(1,input.length-1))
                } else {
                    input
                }
            } else {
                input
            }
        }

        private fun findLeftParenthesisFromRight(input: String): Int {
            var level = 0
            for (i in input.length-1 downTo 0 ) {
                if (input[i] == ')') {
                    level++
                } else if (input[i] == '(') {
                    level--
                    if (level == 0)
                        return i
                }
            }
            return -1
        }

        private fun findOperator(input: String, operator: Char): Int {
            var level = 0
            for (i in input.indices) {
                if (input[i] == '(') {
                    level++
                } else if (input[i] == ')') {
                    level--
                } else if (input[i] == operator && level == 0) {
                    return i
                }
            }
            return -1
        }

    }
}

class Expression(
    private val left: Expr,
    private val right: Expr,
    private val operator: Char): Expr() {

    override fun getValue(): Long {
        return when (operator) {
            '+' -> left.getValue() + right.getValue()
            '*' -> left.getValue() * right.getValue()
            else -> throw Exception("Weird operation")
        }
    }
}

class Operand(private val number: Long): Expr() {
    override fun getValue() = number
}




