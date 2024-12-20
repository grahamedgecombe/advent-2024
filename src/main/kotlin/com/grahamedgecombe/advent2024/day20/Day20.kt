package com.grahamedgecombe.advent2024.day20

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2
import kotlin.math.abs

object Day20 : Puzzle<Map<Vector2, Int>>(20) {
    private val DIRECTIONS = setOf(
        Vector2(-1, 0),
        Vector2(1, 0),
        Vector2(0, -1),
        Vector2(0, 1),
    )

    override fun parse(input: Sequence<String>): Map<Vector2, Int> {
        return race(CharGrid.parse(input.toList(), '#'))
    }

    override fun solvePart1(input: Map<Vector2, Int>): Int {
        return solve(input, 2)
    }

    override fun solvePart2(input: Map<Vector2, Int>): Int {
        return solve(input, 20)
    }

    private fun solve(times: Map<Vector2, Int>, maxCheat: Int): Int {
        var count = 0

        for ((u, t1) in times) {
            for (dy in -maxCheat..maxCheat) {
                for (dx in -maxCheat..maxCheat) {
                    val dist = abs(dx) + abs(dy)
                    if (dist > maxCheat) {
                        continue
                    }

                    val v = Vector2(u.x + dx, u.y + dy)
                    val t2 = times[v] ?: continue

                    if ((t2 - t1 - dist) >= 100) {
                        count++
                    }
                }
            }
        }

        return count
    }

    private fun race(grid: CharGrid): Map<Vector2, Int> {
        val times = mutableMapOf<Vector2, Int>()

        var current = grid.find('S') ?: throw UnsolvableException()
        var previous: Vector2? = null
        var time = 0

        while (true) {
            times[current] = time

            if (grid[current] == 'E') {
                return times
            }

            var stuck = true

            for (direction in DIRECTIONS) {
                val next = current + direction
                if (next == previous || grid[next] == '#') {
                    continue
                }

                previous = current
                current = next
                time++
                stuck = false
                break
            }

            if (stuck) {
                throw UnsolvableException()
            }
        }
    }
}
