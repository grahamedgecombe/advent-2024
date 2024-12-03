package com.grahamedgecombe.advent2024.day3

import com.grahamedgecombe.advent2024.Puzzle

object Day3 : Puzzle<List<Pair<Int, Int>>>(3) {
    private val REGEX = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

    override fun parse(input: Sequence<String>): List<Pair<Int, Int>> {
        return buildList {
            for (line in input) {
                for (match in REGEX.findAll(line)) {
                    val (a, b) = match.destructured
                    add(Pair(a.toInt(), b.toInt()))
                }
            }
        }
    }

    override fun solvePart1(input: List<Pair<Int, Int>>): Int {
        return input.sumOf { (a, b) -> a * b }
    }
}
