package com.grahamedgecombe.advent2024.day15

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day15 : Puzzle<Day15.Input>(15) {
    data class Input(val grid: CharGrid, val moves: List<Vector2>)

    override fun parse(input: Sequence<String>): Input {
        val it = input.iterator()

        val grid = CharGrid.parse(buildList {
            while (it.hasNext()) {
                val line = it.next()
                if (line.isEmpty()) {
                    break
                }
                add(line)
            }
        }, '#')

        val moves = buildList {
            while (it.hasNext()) {
                val line = it.next()
                for (c in line) {
                    val move = when (c) {
                        '^' -> Vector2(0, -1)
                        'v' -> Vector2(0, 1)
                        '<' -> Vector2(-1, 0)
                        '>' -> Vector2(1, 0)
                        else -> throw IllegalArgumentException()
                    }
                    add(move)
                }
            }
        }

        return Input(grid, moves)
    }

    override fun solvePart1(input: Input): Int {
        val grid = CharGrid(input.grid)

        var position = grid.find('@') ?: throw IllegalArgumentException()
        grid[position] = '.'

        for (move in input.moves) {
            val next = position + move

            var v = next
            var empty: Boolean

            while (true) {
                val tile = grid[v]
                if (tile == '.') {
                    empty = true
                    break
                } else if (tile == '#') {
                    empty = false
                    break
                } else {
                    check(tile == 'O')
                    v += move
                }
            }

            if (!empty) {
                continue
            }

            val tile = grid[next]
            grid[next] = grid[v]
            grid[v] = tile

            position = next
        }

        var sum = 0

        for ((position, tile) in grid) {
            if (tile == 'O') {
                sum += position.y * 100 + position.x
            }
        }

        return sum
    }
}
