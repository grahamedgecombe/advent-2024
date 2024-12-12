package com.grahamedgecombe.advent2024.day11

import com.grahamedgecombe.advent2024.Puzzle

object Day11 : Puzzle<List<Long>>(11) {
    override fun parse(input: Sequence<String>): List<Long> {
        return input.single().split(' ').map(String::toLong)
    }

    override fun solvePart1(input: List<Long>): Int {
        var stones = input
        repeat(25) {
            stones = blink(stones)
        }
        return stones.size
    }

    private fun blink(stones: List<Long>): List<Long> {
        val next = mutableListOf<Long>()

        for (stone in stones) {
            if (stone == 0L) {
                next += 1
                continue
            }

            val digits = stone.toString()
            if (digits.length % 2 == 0) {
                next += digits.slice(0 until digits.length / 2).toLong()
                next += digits.slice(digits.length / 2 until digits.length).toLong()
            } else {
                next += stone * 2024
            }
        }

        return next
    }
}
