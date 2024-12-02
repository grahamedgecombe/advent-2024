package com.grahamedgecombe.advent2024.day2

import com.grahamedgecombe.advent2024.Puzzle
import kotlin.math.abs

object Day2 : Puzzle<List<List<Int>>>(2) {
    override fun parse(input: Sequence<String>): List<List<Int>> {
        return input.map { line ->
            line.split(' ').map(String::toInt).toList()
        }.toList()
    }

    override fun solvePart1(input: List<List<Int>>): Int {
        return input.count(::isSafe)
    }

    private fun isSafe(report: List<Int>): Boolean {
        val pairs = report.zipWithNext()
        if (!pairs.all { (a, b) -> abs(a - b) in 1..3 }) {
            return false
        }

        return pairs.all { (a, b) -> a > b } || pairs.all { (a, b) -> a < b }
    }
}
