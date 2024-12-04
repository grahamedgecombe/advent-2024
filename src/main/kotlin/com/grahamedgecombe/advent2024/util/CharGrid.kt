package com.grahamedgecombe.advent2024.util

class CharGrid private constructor(
    private val tiles: CharArray,
    val width: Int,
    val height: Int,
    private val default: Char,
) {
    operator fun get(v: Vector2): Char {
        return get(v.x, v.y)
    }

    operator fun get(x: Int, y: Int): Char {
        return if (x in 0 until width && y in 0 until height) {
            tiles[y * width + x]
        } else {
            default
        }
    }

    companion object {
        fun parse(lines: List<String>, default: Char): CharGrid {
            require(lines.isNotEmpty())

            val height = lines.size
            val width = lines.first().length

            val tiles = CharArray(width * height)

            var i = 0
            for (line in lines) {
                require(line.length == width)

                for (c in line) {
                    tiles[i++] = c
                }
            }

            return CharGrid(tiles, width, height, default)
        }
    }
}
