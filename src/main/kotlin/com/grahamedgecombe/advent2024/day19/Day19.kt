package com.grahamedgecombe.advent2024.day19

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day19.Day19.Input

object Day19 : Puzzle<Input>(19) {
    data class Input(val patterns: List<String>, val designs: List<String>)

    override fun parse(input: Sequence<String>): Input {
        val it = input.iterator()

        require(it.hasNext())
        val patterns = it.next().split(", ").toList()

        require(it.hasNext() && it.next().isEmpty())

        val designs = buildList {
            while (it.hasNext()) {
                add(it.next())
            }
        }

        return Input(patterns, designs)
    }

    override fun solvePart1(input: Input): Int {
        return input.designs.count { design -> countValid(input.patterns, design, mutableMapOf()) > 0 }
    }

    override fun solvePart2(input: Input): Long {
        return input.designs.sumOf { design -> countValid(input.patterns, design, mutableMapOf()) }
    }

    private fun countValid(patterns: List<String>, design: String, cache: MutableMap<String, Long>): Long {
        if (design.isEmpty()) {
            return 1
        }

        var count = cache[design]
        if (count != null) {
            return count
        }

        count = 0

        for (pattern in patterns) {
            if (design.startsWith(pattern)) {
                count += countValid(patterns, design.substring(pattern.length), cache)
            }
        }

        cache[design] = count
        return count
    }
}
