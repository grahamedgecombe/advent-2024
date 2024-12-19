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
        return input.designs.count { design -> isValid(input.patterns, design, mutableMapOf()) }
    }

    private fun isValid(patterns: List<String>, design: String, cache: MutableMap<String, Boolean>): Boolean {
        if (design.isEmpty()) {
            return true
        }

        val valid = cache[design]
        if (valid != null) {
            return valid
        }

        for (pattern in patterns) {
            if (design.startsWith(pattern) && isValid(patterns, design.substring(pattern.length), cache)) {
                cache[design] = true
                return true
            }
        }

        cache[design] = false
        return false
    }
}
