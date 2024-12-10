package com.grahamedgecombe.advent2024.util

class CharGrid private constructor(
    private val tiles: CharArray,
    val width: Int,
    val height: Int,
    private val default: Char,
) : Iterable<Pair<Vector2, Char>> {
    fun find(c: Char): Vector2? {
        var i = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (tiles[i++] == c) {
                    return Vector2(x, y)
                }
            }
        }

        return null
    }

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

    override fun iterator(): Iterator<Pair<Vector2, Char>> {
        return iterator {
            var i = 0
            for (y in 0 until height) {
                for (x in 0 until width) {
                    yield(Pair(Vector2(x, y), tiles[i++]))
                }
            }
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
