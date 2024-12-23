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

    override fun solvePart2(input: Set<Pair<String, String>>): String {
        val edges = mutableMapOf<String, MutableSet<String>>()

        for ((a, b) in input) {
            edges.getOrPut(a, ::mutableSetOf) += b
            edges.getOrPut(b, ::mutableSetOf) += a
        }

        val clique = bronKerbosch2(edges, emptySet(), edges.keys, emptySet()).maxBy(Set<String>::size)
        return clique.sorted().joinToString(",")
    }

    private fun bronKerbosch2(
        edges: Map<String, Set<String>>,
        r: Set<String>,
        p: Set<String>,
        x: Set<String>,
    ): Sequence<Set<String>> = sequence {
        if (p.isEmpty() && x.isEmpty()) {
            yield(r)
            return@sequence
        }

        val u = p.union(x).first()

        var p = p
        var x = x

        for (v in p.minus(edges.getOrDefault(u, emptySet()))) {
            val n = edges.getOrDefault(v, emptySet())

            yieldAll(bronKerbosch2(edges, r.plus(v), p.intersect(n), x.intersect(n)))

            p = p.minus(v)
            x = x.plus(v)
        }
    }
}
