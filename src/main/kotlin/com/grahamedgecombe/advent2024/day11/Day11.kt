package com.grahamedgecombe.advent2024.day11

import com.grahamedgecombe.advent2024.Puzzle

object Day11 : Puzzle<List<Long>>(11) {
    override fun parse(input: Sequence<String>): List<Long> {
        return input.single().split(' ').map(String::toLong)
    }

    override fun solvePart1(input: List<Long>): Long {
        return solve(input, 25)
    }

    override fun solvePart2(input: List<Long>): Long {
        return solve(input, 75)
    }

    private fun solve(input: List<Long>, blinks: Int): Long {
        var frequencies = buildMap<Long, Long> {
            for (stone in input) {
                add(stone, 1)
            }
        }

        repeat(blinks) {
            frequencies = blink(frequencies)
        }

        return frequencies.values.sum()
    }

    private fun blink(frequencies: Map<Long, Long>): Map<Long, Long> {
        val next = mutableMapOf<Long, Long>()

        for ((stone, count) in frequencies) {
            if (stone == 0L) {
                next.add(1, count)
                continue
            }

            val digits = stone.toString()
            if (digits.length % 2 == 0) {
                next.add(digits.slice(0 until digits.length / 2).toLong(), count)
                next.add(digits.slice(digits.length / 2 until digits.length).toLong(), count)
            } else {
                next.add(stone * 2024, count)
            }
        }

        return next
    }

    private fun MutableMap<Long, Long>.add(k: Long, v: Long) {
        this[k] = getOrDefault(k, 0) + v
    }
}
