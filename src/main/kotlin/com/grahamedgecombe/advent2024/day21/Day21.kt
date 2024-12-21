package com.grahamedgecombe.advent2024.day21

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day21 : Puzzle<List<String>>(21) {
    private val DIRECTIONS = setOf(
        '<' to Vector2(-1, 0),
        '>' to Vector2(1, 0),
        '^' to Vector2(0, -1),
        'v' to Vector2(0, 1),
    )
    private val NUMERIC_KEYPAD = findShortestPaths(CharGrid.parse(listOf(
        "789",
        "456",
        "123",
        " 0A",
    ), ' '))
    private val DIRECTIONAL_KEYPAD = findShortestPaths(CharGrid.parse(listOf(
        " ^A",
        "<v>",
    ), ' '))

    private fun findShortestPaths(keypad: CharGrid): Map<Pair<Char, Char>, Set<String>> {
        return buildMap {
            for ((u, a) in keypad) {
                if (a == ' ') {
                    continue
                }

                for ((v, b) in keypad) {
                    if (b != ' ') {
                        this[a to b] = findShortestPaths(keypad, u, v)
                    }
                }
            }
        }
    }

    private fun findShortestPaths(keypad: CharGrid, from: Vector2, to: Vector2): Set<String> {
        val distances = bfs(keypad, from, to)
        val paths = mutableSetOf<String>()
        dfs(distances, from, to, "", paths)
        return paths
    }

    private fun bfs(keypad: CharGrid, from: Vector2, to: Vector2): Map<Vector2, Int> {
        val distances = mutableMapOf<Vector2, Int>()
        distances[from] = 0

        val queue = ArrayDeque<Vector2>()
        queue += from

        while (true) {
            val node = queue.removeFirstOrNull() ?: break
            if (node == to) {
                break
            }

            for ((_, direction) in DIRECTIONS) {
                val neighbour = node + direction
                if (keypad[neighbour] != ' ' && neighbour !in distances) {
                    distances[neighbour] = distances[node]!! + 1
                    queue += neighbour
                }
            }
        }

        return distances
    }

    private fun dfs(distances: Map<Vector2, Int>, node: Vector2, to: Vector2, path: String, paths: MutableSet<String>) {
        if (node == to) {
            paths += path
            return
        }

        val depth = distances[node]!!

        for ((c, direction) in DIRECTIONS) {
            val neighbour = node + direction
            val neighbourDepth = distances[neighbour] ?: continue
            if (neighbourDepth == depth + 1) {
                dfs(distances, neighbour, to, path + c, paths)
            }
        }
    }

    override fun parse(input: Sequence<String>): List<String> {
        return input.toList()
    }

    private data class Key(val robot: Int, val from: Char, val to: Char)

    private fun minLength(input: String, robots: Int): Long {
        val cache = mutableMapOf<Key, Long>()

        var len = 0L
        for ((a, b) in "A$input".zipWithNext()) {
            len += minLength(robots, robots, a, b, cache)
        }
        return len
    }

    private fun minLength(
        robot: Int,
        robots: Int,
        from: Char,
        to: Char,
        cache: MutableMap<Key, Long>,
    ): Long {
        if (robot == 0) {
            return 1
        }

        val key = Key(robot, from, to)

        var best = cache[key]
        if (best != null) {
            return best
        }

        val move = Pair(from, to)
        val keypad = if (robot == robots) NUMERIC_KEYPAD else DIRECTIONAL_KEYPAD

        val sequences = keypad[move] ?: throw UnsolvableException()

        best = sequences.minOf { sequence ->
            var len = 0L
            for ((a, b) in "A${sequence}A".zipWithNext()) {
                len += minLength(robot - 1, robots, a, b, cache)
            }
            len
        }
        cache[key] = best
        return best
    }

    override fun solvePart1(input: List<String>): Long {
        return solve(input, 3)
    }

    override fun solvePart2(input: List<String>): Long {
        return solve(input, 26)
    }

    private fun solve(input: List<String>, robots: Int): Long {
        return input.sumOf { code ->
            minLength(code, robots) * code.filter(Char::isDigit).toLong()
        }
    }
}
