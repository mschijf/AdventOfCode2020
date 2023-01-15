package com.adventofcode.december20

class Image(arrangement: Array<Array<TileConfig?>>) {
    private val compressedImage = mergeTiles(arrangement)
    private val seaMonsterPattern = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   ")

    fun getRoughNess(): Long {
        val count = countMonsters()
        val roughness = compressedImage.sumOf { it.count{ch -> ch == '#'}} - count*seaMonsterPattern.sumOf { it.count{ch -> ch == '#'}}
        return roughness.toLong()
    }

    private fun mergeTiles(arrangement: Array<Array<TileConfig?>>): List<String> {
        val result = mutableListOf<String>()
        val compressed = arrangement.map{ rows -> rows.map{ tileCfg -> tileCfg!!.removeBorder()} }
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

    private fun countMonsters(): Int {
        var count = 0
        for (row in 1 until compressedImage.size-1) {
            var index = findFirst(seaMonsterPattern[1], compressedImage[row])
            while (index != -1) {
                val patternMatch = startsWith(seaMonsterPattern[0], compressedImage[row-1].substring(index)) &&
                        startsWith(seaMonsterPattern[2],compressedImage[row+1].substring(index))

                if (patternMatch) {
                    count++
                }
                index = findFirst(seaMonsterPattern[1], compressedImage[row], index+1)
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