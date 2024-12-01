package com.grahamedgecombe.advent2024.day1

import com.grahamedgecombe.advent2024.Puzzle
import kotlin.collections.sorted
import kotlin.math.abs

object Day1 : Puzzle<Pair<List<Int>, List<Int>>>(1) {
    private val WS = Regex("\\s+")

    override fun parse(input: Sequence<String>): Pair<List<Int>, List<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        for (line in input) {
            val (l, r) = line.split(WS, limit = 2)
            left += l.toInt()
            right += r.toInt()
        }

        return Pair(left, right)
    }

    override fun solvePart1(input: Pair<List<Int>, List<Int>>): Int {
        val left = input.first.sorted()
        val right = input.second.sorted()
        return left.zip(right).sumOf { abs(it.first - it.second) }
    }
}
