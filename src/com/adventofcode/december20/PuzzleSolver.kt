package com.adventofcode.december20

import com.adventofcode.PuzzleSolverAbstract
import com.adventofcode.mylambdas.splitByCondition
import java.lang.Long.min
import kotlin.math.sqrt

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    val tileList = input.inputLines.splitByCondition { it.isEmpty() }.map { Tile(it) }

    override fun resultPartOne(): String {
        tileList.forEach {it.updateMatchList(tileList)}
        val x = solver(tileList.toSet(), 0, 0)
        return x.toString()
    }

    override fun resultPartTwo(): String {
        tileList.forEach {it.updateMatchList(tileList)}
        val countMonster = solver(tileList.toSet(), 0, 0)
        return countMonster.toString()
    }

    val puzzleWidth = sqrt(tileList.size.toDouble() + 0.01).toInt()
    val finalPuzzle = Array(puzzleWidth){ arrayOfNulls<TileConfig>(puzzleWidth)  }

    private fun solver(tilesLeft: Set<Tile>, newRow: Int, newCol:Int): Long {
        if (tilesLeft.isEmpty()) {
//            println(finalPuzzle[0][0]!!.tile.id *
//                    finalPuzzle[0][puzzleWidth-1]!!.tile.id *
//                    finalPuzzle[puzzleWidth-1][puzzleWidth-1]!!.tile.id *
//                    finalPuzzle[puzzleWidth-1][0]!!.tile.id)
//
            val compressedImage = mergeTiles()
            val count = countMonsters(compressedImage)
            val roughness = compressedImage.sumOf { it.count{ch -> ch == '#'}} - count*SeaMonster.pattern.sumOf { it.count{ch -> ch == '#'}}
            println(roughness)
            return roughness.toLong()
        }
        if (newRow > puzzleWidth)
            throw Exception("Did not expect that")

        val possibleTileSet = if (newRow > 0 && newCol > 0) {
            val neighbourTile1 = finalPuzzle[newRow-1][newCol]!!
            val neighbourTile2 = finalPuzzle[newRow][newCol-1]!!
            neighbourTile1.tile.matchList[neighbourTile1.config].bottomMatch intersect neighbourTile2.tile.matchList[neighbourTile2.config].rightMatch
        } else if (newRow > 0) {
            val neighbourTile = finalPuzzle[newRow-1][newCol]!!
            neighbourTile.tile.matchList[neighbourTile.config].bottomMatch
        } else if (newCol > 0) {
            val neighbourTile = finalPuzzle[newRow][newCol-1]!!
            neighbourTile.tile.matchList[neighbourTile.config].rightMatch
        } else {
            tilesLeft.map { tile -> List(tile.gridConfigList.size){ configIndex -> TileConfig(tile, configIndex)} }.flatten()
        }

        var minCount = 99999999L
        possibleTileSet.forEach {aTileConfig ->
            finalPuzzle[newRow][newCol] = aTileConfig
            val nextCol = (newCol+1) % puzzleWidth
            val nextRow = if (nextCol == 0) newRow+1 else newRow
            minCount = min(minCount, solver(tilesLeft.minus(aTileConfig.tile), nextRow, nextCol))
            finalPuzzle[newRow][newCol] = null
        }

        return minCount
    }

    private fun mergeTiles(): List<String> {
        val result = mutableListOf<String>()
        val compressed = finalPuzzle.map{ rows -> rows.map{tileCfg -> tileCfg!!.removeBorder()} }
        for (puzzleRow in compressed.indices) {
            for (tileRow in compressed[puzzleRow][0].indices) {
                val totalRow = mutableListOf<Char>()
                for (puzzleCol in compressed[puzzleRow].indices) {
                    totalRow += compressed[puzzleRow][puzzleCol][tileRow]
                }
                result.add(totalRow.joinToString(""))
            }
        }
        return result
    }

    private fun countMonsters(image: List<String>): Int {
        var count = 0
        for (row in 1 until image.size-1) {
            var index = findFirst(SeaMonster.pattern[1], image[row])
            while (index != -1) {
                val patternMatch = startsWith(SeaMonster.pattern[0], image[row-1].substring(index)) &&
                        startsWith(SeaMonster.pattern[2],image[row+1].substring(index))

                if (patternMatch) {
                    count++
                }
                index = findFirst(SeaMonster.pattern[1], image[row], index+1)
            }
        }
        return count
    }

    private fun findFirst(needle: String, hayStack: String, startIndex: Int=0): Int {
        var index = startIndex
        while (index + needle.length < hayStack.length) {
            if (startsWith(needle, hayStack.substring(index)))
                return index
            index++
        }
        return -1
    }

    private fun startsWith(needle: String, hayStack: String): Boolean {
        if (needle.length > hayStack.length)
            return false
        for (index in needle.indices) {
            if (needle[index] == '#' && hayStack[index] != '#')
                return false
        }
        return true
    }
}


class Tile(inputLines: List<String>) {
    val id = inputLines.first().substringAfter("Tile ").substringBefore(":").toLong()
    val startGrid = inputLines.drop(1).map{it.toList()}

    val gridConfigList = listOf(
        startGrid,
        rotate90(startGrid),
        rotate90(rotate90(startGrid)),
        rotate90(rotate90(rotate90(startGrid))),
        flip(startGrid),
        rotate90(flip(startGrid)),
        rotate90(rotate90(flip(startGrid))),
        rotate90(rotate90(rotate90(flip(startGrid))))
    )

    private fun flip(aGrid: List<List<Char>>): List<List<Char>> {
        return aGrid.mapIndexed{ row, rows -> List(rows.size) { col -> aGrid[row][rows.size - col - 1] } }
    }

    private fun rotate90(aGrid: List<List<Char>>): List<List<Char>> {
        return aGrid.mapIndexed{ row, rows -> List(rows.size) { col -> aGrid[aGrid.size - 1 - col][row] } }
    }

    private fun upperMatch(thisGrid: List<List<Char>>, otherGrid: List<List<Char>>): Boolean {
        return (startGrid.indices).all{ col-> thisGrid[0][col] == otherGrid[startGrid.size-1][col]}
    }
    private fun rightMatch(thisGrid: List<List<Char>>, otherGrid: List<List<Char>>): Boolean {
        return (startGrid.indices).all{ row-> thisGrid[row][startGrid.size-1] == otherGrid[row][0]}
    }
    private fun bottomMatch(thisGrid: List<List<Char>>, otherGrid: List<List<Char>>): Boolean {
        return (startGrid.indices).all{ col-> thisGrid[startGrid.size-1][col] == otherGrid[0][col]}
    }
    private fun leftMatch(thisGrid: List<List<Char>>, otherGrid: List<List<Char>>): Boolean {
        return (startGrid.indices).all{ row-> thisGrid[row][0] == otherGrid[row][startGrid.size-1]}
    }



    val matchList = MutableList<MatchWith>(8){MatchWith()}

    fun updateMatchList(allTiles: List<Tile>) {
        allTiles.forEach {otherTile ->
            if (this != otherTile)
                updateMatch(otherTile)
        }
    }

    private fun updateMatch(other: Tile) {
        for (configMe in 0 until 8) {
            for (configOther in 0 until 8) {
                if (upperMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].upperMatch.add(TileConfig(other, configOther))
                if (rightMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].rightMatch.add(TileConfig(other, configOther))
                if (bottomMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].bottomMatch.add(TileConfig(other, configOther))
                if (leftMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].leftMatch.add(TileConfig(other, configOther))
            }
        }
    }
}

class MatchWith{
    val upperMatch = mutableSetOf<TileConfig>()
    val rightMatch = mutableSetOf<TileConfig>()
    val bottomMatch = mutableSetOf<TileConfig>()
    val leftMatch = mutableSetOf<TileConfig>()
}

data class TileConfig(val tile: Tile, val config: Int) {
    fun removeBorder(): List<List<Char>> {
        return tile.gridConfigList[config]
            .filterIndexed{index, _ -> index != 0 && index != tile.gridConfigList[config].size-1}
            .map { rows -> rows.filterIndexed{ col, _ -> col != 0 && col != tile.gridConfigList[config].size-1} }
    }

}

object SeaMonster{
    val pattern = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   ")
}