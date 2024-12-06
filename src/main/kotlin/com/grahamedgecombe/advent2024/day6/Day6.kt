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
        var position = input.find('^') ?: throw UnsolvableException()
        var dir = Vector2(0, -1)

        val visited = mutableSetOf<Vector2>()

        while (position.x in 0 until input.width && position.y in 0 until input.height) {
            visited += position

            val next = position + dir
            if (input[next] == '#') {
                dir = Vector2(-dir.y, dir.x)
            } else {
                position = next
            }
        }

        return visited.size
    }
}
