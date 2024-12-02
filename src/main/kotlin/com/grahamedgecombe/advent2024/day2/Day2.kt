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
        return input.count(::isSafePart1)
    }

    override fun solvePart2(input: List<List<Int>>): Int {
        return input.count(::isSafePart2)
    }

    private fun isSafePart1(report: List<Int>): Boolean {
        val pairs = report.zipWithNext()
        if (!pairs.all { (a, b) -> abs(a - b) in 1..3 }) {
            return false
        }

        return pairs.all { (a, b) -> a > b } || pairs.all { (a, b) -> a < b }
    }

    private fun isSafePart2(report: List<Int>): Boolean {
        for (i in report.indices) {
            if (isSafePart1(report.slice(0 until i) + report.slice(i + 1 until report.size))) {
                return true
            }
        }

        return false
    }
}
