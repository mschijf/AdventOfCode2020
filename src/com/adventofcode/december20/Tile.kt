package com.adventofcode.december20

class Tile(inputLines: List<String>) {
    val id = inputLines.first().substringAfter("Tile ").substringBefore(":").toLong()
    private val startGrid = inputLines.drop(1).map{it.toList()}
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
                    matchList[configMe].upperMatch.add(TileConfig(other, configOther, other.gridConfigList[configOther]))
                if (rightMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].rightMatch.add(TileConfig(other, configOther, other.gridConfigList[configOther]))
                if (bottomMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].bottomMatch.add(TileConfig(other, configOther, other.gridConfigList[configOther]))
                if (leftMatch(gridConfigList[configMe], other.gridConfigList[configOther]))
                    matchList[configMe].leftMatch.add(TileConfig(other, configOther, other.gridConfigList[configOther]))
            }
        }
    }

    inner class MatchWith{
        val upperMatch = mutableSetOf<TileConfig>()
        val rightMatch = mutableSetOf<TileConfig>()
        val bottomMatch = mutableSetOf<TileConfig>()
        val leftMatch = mutableSetOf<TileConfig>()
    }
}


data class TileConfig(
    val tile: Tile,
    val config: Int,
    val grid: List<List<Char>> = emptyList()) {


    val upperMatch = mutableSetOf<TileConfig>()
    val rightMatch = mutableSetOf<TileConfig>()
    val bottomMatch = mutableSetOf<TileConfig>()
    val leftMatch = mutableSetOf<TileConfig>()


    fun upperMatch() = tile.matchList[config].upperMatch
    fun rightMatch() = tile.matchList[config].rightMatch
    fun bottomMatch() = tile.matchList[config].bottomMatch
    fun leftMatch() = tile.matchList[config].leftMatch

    fun removeBorder(): List<List<Char>> {
        return tile.gridConfigList[config]
            .filterIndexed{index, _ -> index != 0 && index != tile.gridConfigList[config].size-1}
            .map { rows -> rows.filterIndexed{ col, _ -> col != 0 && col != tile.gridConfigList[config].size-1} }
    }


}
