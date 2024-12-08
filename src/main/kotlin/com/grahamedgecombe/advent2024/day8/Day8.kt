package com.grahamedgecombe.advent2024.day8

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day8.Day8.Input
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day8 : Puzzle<Input>(8) {
    data class Input(val width: Int, val height: Int, val antennae: List<Antenna>)
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
        val antinodes = mutableSetOf<Vector2>()

        for (a in input.antennae) {
            for (b in input.antennae) {
                if (a == b || a.frequency != b.frequency) {
                    continue
                }

                val delta = a.position - b.position
                antinodes += a.position + delta
            }
        }

        return antinodes.filter { (x, y) ->
            x in 0 until input.width && y in 0 until input.height
        }.size
    }
}
