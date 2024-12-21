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
    private const val ROBOTS = 3

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

    private fun minLength(input: String): Int {
        return minLength(ROBOTS, "A$input", "")
    }

    private fun minLength(
        robot: Int,
        input: String,
        output: String
    ): Int {
        if (robot == 0) {
            return input.length - 1
        } else if (input.length < 2) {
            return minLength(robot - 1, "A$output", "")
        }

        val move = Pair(input[0], input[1])
        val keypad = if (robot == ROBOTS) NUMERIC_KEYPAD else DIRECTIONAL_KEYPAD
        val tail = input.substring(1)

        val sequences = keypad[move] ?: throw UnsolvableException()

        var best = Int.MAX_VALUE
        for (sequence in sequences) {
            best = minOf(best, minLength(robot, tail, output + sequence + "A"))
        }
        return best
    }

    override fun solvePart1(input: List<String>): Int {
        return input.sumOf { code ->
            minLength(code) * code.filter(Char::isDigit).toInt()
        }
    }
}
