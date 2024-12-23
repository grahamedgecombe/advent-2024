package com.grahamedgecombe.advent2024.day23

import com.grahamedgecombe.advent2024.Puzzle

object Day23 : Puzzle<Set<Pair<String, String>>>(23) {
    override fun parse(input: Sequence<String>): Set<Pair<String, String>> {
        return input.map { line ->
            val (a, b) = line.split("-", limit = 2)
            Pair(a, b)
        }.toSet()
    }

    override fun solvePart1(input: Set<Pair<String, String>>): Int {
        val nodes = input.flatMap { (a, b) -> sequenceOf(a, b) }.distinct()
        var count = 0

        for ((i, a) in nodes.withIndex()) {
            for ((j, b) in nodes.subList(0, i).withIndex()) {
                if (!isConnected(input, a, b)) {
                    continue
                }

                for (c in nodes.subList(0, j)) {
                    if (!isConnected(input, a, c) || !isConnected(input, b, c)) {
                        continue
                    }

                    if (a.startsWith("t") || b.startsWith("t") || c.startsWith("t")) {
                        count++
                    }
                }
            }
        }

        return count
    }

    private fun isConnected(edges: Set<Pair<String, String>>, a: String, b: String): Boolean {
        return Pair(a, b) in edges || Pair(b, a) in edges
    }
}
