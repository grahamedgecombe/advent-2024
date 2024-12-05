package com.grahamedgecombe.advent2024.day5

import com.grahamedgecombe.advent2024.Puzzle

object Day5 : Puzzle<Day5.Input>(5) {
    data class Input(
        val rules: Set<Pair<Int, Int>>,
        val updates: List<List<Int>>
    )

    override fun parse(input: Sequence<String>): Input {
        val it = input.iterator()

        val rules = buildSet {
            while (it.hasNext()) {
                val line = it.next()
                if (line.isEmpty()) {
                    return@buildSet
                }

                val (before, after) = line.split('|', limit = 2)
                add(Pair(before.toInt(), after.toInt()))
            }
        }

        val updates = buildList {
            while (it.hasNext()) {
                val line = it.next()
                add(line.split(',').map(String::toInt).toList())
            }
        }

        return Input(rules, updates)
    }

    override fun solvePart1(input: Input): Int {
        var sum = 0

        for (update in input.updates) {
            val sorted = sort(input.rules, update)
            if (update == sorted) {
                sum += update[update.size / 2]
            }
        }

        return sum
    }

    override fun solvePart2(input: Input): Int {
        var sum = 0

        for (update in input.updates) {
            val sorted = sort(input.rules, update)
            if (update != sorted) {
                sum += sorted[sorted.size / 2]
            }
        }

        return sum
    }

    private fun sort(rules: Set<Pair<Int, Int>>, update: List<Int>): List<Int> {
        return update.sortedWith { a, b ->
            when {
                Pair(a, b) in rules -> -1
                Pair(b, a) in rules -> 1
                else -> 0
            }
        }
    }
}
