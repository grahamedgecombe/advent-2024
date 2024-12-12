package com.grahamedgecombe.advent2024.day12

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day12 : Puzzle<CharGrid>(12) {
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
        val regions = mutableSetOf<Set<Vector2>>()
        val visited = mutableSetOf<Vector2>()

        for (y in 0 until input.height) {
            for (x in 0 until input.width) {
                val v = Vector2(x, y)
                if (v in visited) {
                    continue
                }

                val region = bfs(input, v)
                regions += region
                visited.addAll(region)
            }
        }

        return regions.sumOf { region ->
            getPerimeter(input, region) * region.size
        }
    }

    private fun bfs(grid: CharGrid, start: Vector2): Set<Vector2> {
        val visited = mutableSetOf<Vector2>(start)
        val type = grid[start]

        val queue = ArrayDeque<Vector2>()
        queue += start

        while (true) {
            val current = queue.removeFirstOrNull() ?: break

            for (direction in DIRECTIONS) {
                val next = current + direction
                if (grid[next] != type || next in visited) {
                    continue
                }

                visited += next
                queue += next
            }
        }

        return visited
    }

    private fun getPerimeter(grid: CharGrid, region: Set<Vector2>): Int {
        var perimeter = 0

        for (v in region) {
            val type = grid[v]

            for (direction in DIRECTIONS) {
                if (type != grid[v + direction]) {
                    perimeter++
                }
            }
        }

        return perimeter
    }
}
