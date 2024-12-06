package com.grahamedgecombe.advent2024.day6

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day6 : Puzzle<CharGrid>(6) {
    override fun parse(input: Sequence<String>): CharGrid {
        return CharGrid.parse(input.toList(), '.')
    }

    override fun solvePart1(input: CharGrid): Int {
        val start = input.find('^') ?: throw UnsolvableException()
        return getVisitedPositions(input, start).size
    }

    private fun getVisitedPositions(grid: CharGrid, start: Vector2): Set<Vector2> {
        var position = start
        var dir = Vector2(0, -1)

        val visited = mutableSetOf<Vector2>()

        while (position.x in 0 until grid.width && position.y in 0 until grid.height) {
            visited += position

            val next = position + dir
            if (grid[next] == '#') {
                dir = Vector2(-dir.y, dir.x)
            } else {
                position = next
            }
        }

        return visited
    }

    override fun solvePart2(input: CharGrid): Int {
        val start = input.find('^') ?: throw UnsolvableException()
        return getVisitedPositions(input, start).count { obstruction -> isLoop(input, start, obstruction) }
    }

    private fun isLoop(grid: CharGrid, start: Vector2, obstruction: Vector2): Boolean {
        var position = start
        var dir = Vector2(0, -1)

        val visited = mutableSetOf<Pair<Vector2, Vector2>>()

        while (position.x in 0 until grid.width && position.y in 0 until grid.height) {
            if (!visited.add(Pair(position, dir))) {
                return true
            }

            val next = position + dir
            if (grid[next] == '#' || next == obstruction) {
                dir = Vector2(-dir.y, dir.x)
            } else {
                position = next
            }
        }

        return false
    }
}
