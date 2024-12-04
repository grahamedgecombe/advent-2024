package com.grahamedgecombe.advent2024.day4

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day4 : Puzzle<CharGrid>(4) {
    private val DIRECTIONS = buildList {
        for (x in -1..1) {
            for (y in -1..1) {
                if (x != 0 || y != 0) {
                    add(Vector2(x, y))
                }
            }
        }
    }

    override fun parse(input: Sequence<String>): CharGrid {
        return CharGrid.parse(input.toList(), '.')
    }

    override fun solvePart1(input: CharGrid): Int {
        var count = 0

        for (y in 0 until input.height) {
            for (x in 0 until input.width) {
                for (dir in DIRECTIONS) {
                    if (matches(input, x, y, dir)) {
                        count++
                    }
                }
            }
        }

        return count
    }

    override fun solvePart2(input: CharGrid): Int {
        var count = 0

        for (y in 0 until input.height) {
            for (x in 0 until input.width) {
                if (input[x, y] != 'A') {
                    continue
                }

                val tl = input[x - 1, y - 1]
                val tr = input[x + 1, y - 1]
                val bl = input[x - 1, y + 1]
                val br = input[x + 1, y + 1]

                val cross1 = (tl == 'M' && br == 'S') || (tl == 'S' && br == 'M')
                val cross2 = (tr == 'M' && bl == 'S') || (tr == 'S' && bl == 'M')

                if (cross1 && cross2) {
                    count++
                }
            }
        }

        return count
    }

    private fun matches(grid: CharGrid, x: Int, y: Int, dir: Vector2): Boolean {
        for ((i, c) in "XMAS".withIndex()) {
            if (grid[x + dir.x * i, y + dir.y * i] != c) {
                return false
            }
        }

        return true
    }
}
