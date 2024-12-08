package com.grahamedgecombe.advent2024.day8

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day8.Day8.Input
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day8 : Puzzle<Input>(8) {
    data class Input(val width: Int, val height: Int, val antennae: List<Antenna>) {
        operator fun contains(v: Vector2): Boolean {
            return v.x in 0 until width && v.y in 0 until height
        }
    }
    data class Antenna(val position: Vector2, val frequency: Char)

    override fun parse(input: Sequence<String>): Input {
        val grid = CharGrid.parse(input.toList(), '.')

        val antennae = buildList {
            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    val frequency = grid[x, y]
                    if (frequency == '.') {
                        continue
                    }

                    add(Antenna(Vector2(x, y), frequency))
                }
            }
        }

        return Input(grid.width, grid.height, antennae)
    }

    override fun solvePart1(input: Input): Int {
        return solve(input, false)
    }

    override fun solvePart2(input: Input): Int {
        return solve(input, true)
    }

    private fun solve(input: Input, all: Boolean): Int {
        val antinodes = mutableSetOf<Vector2>()

        for (a in input.antennae) {
            for (b in input.antennae) {
                if (a == b || a.frequency != b.frequency) {
                    continue
                }

                val delta = a.position - b.position
                if (all) {
                    var antinode = a.position
                    while (antinode in input) {
                        antinodes += antinode
                        antinode += delta
                    }
                } else {
                    val antinode = a.position + delta
                    if (antinode in input) {
                        antinodes += antinode
                    }
                }
            }
        }

        return antinodes.size
    }
}
