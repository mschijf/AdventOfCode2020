package com.adventofcode.december20

import kotlin.math.sqrt

class SquareArrangement(
    private var tileList: List<Tile>,
    private val isPartTwo: Boolean) {
    private val arrangementWidth = sqrt(tileList.size.toDouble() + 0.01).toInt()
    private val arrangement = Array(arrangementWidth){ arrayOfNulls<TileConfig>(arrangementWidth)  }

    fun getArrangement(): Long {
        solver(tileList.toSet(), 0, 0)
        return 0
    }

    private fun solver(tilesLeft: Set<Tile>, newRow: Int, newCol:Int) {
        if (tilesLeft.isEmpty()) {
            if (isPartTwo)
                println("Roughness is: ${Image(arrangement).getRoughNess()}")
            else
                println("Corner Square product is: ${getCornerSquaresProduct()}")
        }
        if (newRow > arrangementWidth)
            throw Exception("Did not expect that")

        val possibleTileSet = getPossibleTileSet(tilesLeft, newRow, newCol)

        possibleTileSet.forEach {aTileConfig ->
            arrangement[newRow][newCol] = aTileConfig
            val nextCol = (newCol+1) % arrangementWidth
            val nextRow = if (nextCol == 0) newRow+1 else newRow
            solver(tilesLeft.minus(aTileConfig.tile), nextRow, nextCol)
            arrangement[newRow][newCol] = null
        }
    }

    private fun getPossibleTileSet(tilesLeft: Set<Tile>, newRow: Int, newCol:Int): Set<TileConfig> {
        return if (newRow > 0 && newCol > 0) {
            arrangement[newRow-1][newCol]!!.bottomMatch() intersect arrangement[newRow][newCol-1]!!.rightMatch()
        } else if (newRow > 0) {
            arrangement[newRow-1][newCol]!!.bottomMatch()
        } else if (newCol > 0) {
            arrangement[newRow][newCol-1]!!.rightMatch()
        } else {
            tilesLeft.map { tile -> List(tile.gridConfigList.size){ configIndex -> TileConfig(tile, configIndex)} }.flatten().toSet()
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private fun getCornerSquaresProduct(): Long {
        return arrangement[0][0]!!.tile.id *
                arrangement[0][arrangementWidth-1]!!.tile.id *
                arrangement[arrangementWidth-1][arrangementWidth-1]!!.tile.id *
                arrangement[arrangementWidth-1][0]!!.tile.id
    }


    //------------------------------------------------------------------------------------------------------------------

}