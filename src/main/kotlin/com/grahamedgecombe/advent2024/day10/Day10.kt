package com.grahamedgecombe.advent2024.day10

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day10 : Puzzle<CharGrid>(10) {
    private val DIRECTIONS = setOf(
        Vector2(-1, 0),
        Vector2(1, 0),
        Vector2(0, -1),
        Vector2(0, 1),
    )

    override fun parse(input: Sequence<String>): CharGrid {
        return CharGrid.parse(input.toList(), '.')
    }

    override fun solvePart1(input: CharGrid): Int {
        var score = 0

        for ((position, height) in input) {
            if (height == '0') {
                score += dfs(input, position).distinct().count()
            }
        }

        return score
    }

    override fun solvePart2(input: CharGrid): Int {
        var score = 0

        for ((position, height) in input) {
            if (height == '0') {
                score += dfs(input, position).count()
            }
        }

        return score
    }

    private fun dfs(grid: CharGrid, position: Vector2): Sequence<Vector2> = sequence {
        val height = grid[position].digitToInt()
        if (height == 9) {
            yield(position)
            return@sequence
        }

        for (direction in DIRECTIONS) {
            val nextPosition = position + direction
            val nextHeight = grid[nextPosition].digitToIntOrNull() ?: continue

            if (nextHeight == height + 1) {
                yieldAll(dfs(grid, nextPosition))
            }
        }
    }
}
