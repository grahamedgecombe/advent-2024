package com.grahamedgecombe.advent2024.day15

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day15 : Puzzle<Day15.Input>(15) {
    data class Input(val grid: CharGrid, val moves: List<Vector2>)

    private val UP = Vector2(0, -1)
    private val DOWN = Vector2(0, 1)
    private val LEFT = Vector2(-1, 0)
    private val RIGHT = Vector2(1, 0)

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
                        '^' -> UP
                        'v' -> DOWN
                        '<' -> LEFT
                        '>' -> RIGHT
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

    override fun solvePart2(input: Input): Int {
        var grid = transform(input.grid)

        var position = grid.find('@') ?: throw IllegalArgumentException()
        grid[position] = '.'

        for (move in input.moves) {
            val next = position + move

            val tile = grid[next]
            if (tile == '.') {
                position = next
                continue
            } else if (tile == '#') {
                continue
            }

            val boxLeft: Vector2
            val boxRight: Vector2
            if (tile == '[') {
                boxLeft = next
                boxRight = next + RIGHT

                check(grid[boxRight] == ']')
            } else {
                check(tile == ']')

                boxLeft = next + LEFT
                boxRight = next

                check(grid[boxLeft] == '[')
            }

            val tempGrid = CharGrid(grid)
            if (push(tempGrid, boxLeft, boxRight, move)) {
                grid = tempGrid
                position = next

                check(grid[position] == '.')
            }
        }

        var sum = 0

        for ((position, tile) in grid) {
            if (tile == '[') {
                sum += position.y * 100 + position.x
            }
        }

        return sum
    }

    private fun push(grid: CharGrid, boxLeft: Vector2, boxRight: Vector2, move: Vector2): Boolean {
        check(grid[boxLeft] == '[' && grid[boxRight] == ']')

        when (move) {
            UP, DOWN -> {
                val destLeft = boxLeft + move
                val destRight = boxRight + move

                val tileLeft = grid[destLeft]
                val tileRight = grid[destRight]

                if (tileLeft == '#' || tileRight == '#') {
                    return false
                }

                val emptyLeft = if (tileLeft == '.') {
                    true
                } else if (tileLeft == '[') {
                    check(tileRight == ']')
                    push(grid, destLeft, destRight, move)
                } else {
                    check(tileLeft == ']' && grid[destLeft + LEFT] == '[')
                    push(grid, destLeft + LEFT, destLeft, move)
                }

                if (!emptyLeft) {
                    return false
                }

                val emptyRight = if (tileRight == '.') {
                    true
                } else if (tileRight == '[') {
                    check(grid[destRight + RIGHT] == ']')
                    push(grid, destRight, destRight + RIGHT, move)
                } else {
                    check(tileLeft == '[' && tileRight == ']')
                    /*
                     * This case is covered above. We don't want to call push()
                     * twice with the same arguments as it has side effects.
                     */
                    true
                }

                if (emptyRight) {
                    check(grid[destLeft] == '.' && grid[destRight] == '.')

                    grid[destLeft] = '['
                    grid[destRight] = ']'

                    grid[boxLeft] = '.'
                    grid[boxRight] = '.'
                }

                return emptyRight
            }
            LEFT -> {
                val dest = boxLeft + LEFT

                val tile = grid[dest]
                if (tile == '#') {
                    return false
                }

                val empty = if (tile == '.') {
                    true
                } else {
                    check(grid[dest + LEFT] == '[' && tile == ']')
                    push(grid, dest + LEFT, dest, move)
                }

                if (empty) {
                    check(grid[dest] == '.')

                    grid[dest] = '['
                    grid[boxLeft] = ']'
                    grid[boxRight] = '.'
                }

                return empty
            }
            RIGHT -> {
                val dest = boxRight + RIGHT

                val tile = grid[dest]
                if (tile == '#') {
                    return false
                }

                val empty = if (tile == '.') {
                    true
                } else {
                    check(tile == '[' && grid[dest + RIGHT] == ']')
                    push(grid, dest, dest + RIGHT, move)
                }

                if (empty) {
                    check(grid[dest] == '.')

                    grid[boxLeft] = '.'
                    grid[boxRight] = '['
                    grid[dest] = ']'
                }

                return empty
            }
            else -> throw IllegalArgumentException()
        }
    }

    private fun transform(narrow: CharGrid): CharGrid {
        val wide = CharGrid(narrow.width * 2, narrow.height, '#')

        for (y in 0 until narrow.height) {
            for (x in 0 until narrow.width) {
                val tiles = when (narrow[x, y]) {
                    '#' -> Pair('#', '#')
                    'O' -> Pair('[', ']')
                    '.' -> Pair('.', '.')
                    '@' -> Pair('@', '.')
                    else -> throw IllegalArgumentException()
                }
                wide[x * 2, y] = tiles.first
                wide[x * 2 + 1, y] = tiles.second
            }
        }

        return wide
    }
}
