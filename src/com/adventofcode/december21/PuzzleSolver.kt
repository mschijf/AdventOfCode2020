package com.adventofcode.december21

import com.adventofcode.PuzzleSolverAbstract

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    private val foodList = input.inputLines.map { Food(it) }
    private val totalAllergenSet = foodList.map { it.allergenSet }.flatten().toSet()
    private val totalIngredientsSet = foodList.map { it.ingredientSet }.flatten().toSet()

    override fun resultPartOne(): String {
        val possibleIngredientsForAllergenes = totalAllergenSet.map{ allergen -> getPossibleIngredientsPerAllergen(allergen) }
        val nonAllergyIngredients = totalIngredientsSet - possibleIngredientsForAllergenes.flatten().toSet()
        val count = nonAllergyIngredients.sumOf { ingr -> foodList.count { food -> food.hasIngredient(ingr) } }
        return count.toString()
    }

    override fun resultPartTwo(): String {
        val possibleIngredientsForAllergenes = totalAllergenSet.map{ allergen -> Pair(allergen, getPossibleIngredientsPerAllergen(allergen).toMutableSet()) }
        do {
            val singleSet = possibleIngredientsForAllergenes.filter { it.second.size == 1 }.map{ it.second }.flatten().toSet()
            for (type in possibleIngredientsForAllergenes) {
                if (type.second.size > 1) {
                    type.second.removeAll(singleSet)
                }
            }
        } while (possibleIngredientsForAllergenes.any { it.second.size > 1 })

        return possibleIngredientsForAllergenes.sortedBy { it.first }.joinToString(",") { it.second.first().name }
    }

    private fun getPossibleIngredientsPerAllergen(allergen: Allergen): Set<Ingredient> {
        var result = emptySet<Ingredient>()
        foodList.filter {food -> food.hasAllergen(allergen) }.forEach {food ->
            result = if (result.isEmpty()) food.ingredientSet else result intersect food.ingredientSet
        }
        return result
    }
}

class Food(inputLine: String) {
    val ingredientSet = inputLine.substringBefore(" (contains").split(" ").map{Ingredient(it)}.toSet()
    val allergenSet = inputLine.substringAfter("(contains ").substringBefore(")").split(", ").map{Allergen(it)}.toSet()
    fun hasAllergen(allergen: Allergen) = allergen in allergenSet
    fun hasIngredient(ingredient: Ingredient) = ingredient in ingredientSet
}

data class Ingredient(val name: String)
data class Allergen(val name: String): Comparable<Allergen> {
    override fun compareTo(other: Allergen): Int {
        return name.compareTo(other.name)
    }
}


