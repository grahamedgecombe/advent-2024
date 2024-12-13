package com.grahamedgecombe.advent2024

import com.grahamedgecombe.advent2024.day1.Day1
import com.grahamedgecombe.advent2024.day10.Day10
import com.grahamedgecombe.advent2024.day11.Day11
import com.grahamedgecombe.advent2024.day12.Day12
import com.grahamedgecombe.advent2024.day13.Day13
import com.grahamedgecombe.advent2024.day2.Day2
import com.grahamedgecombe.advent2024.day3.Day3
import com.grahamedgecombe.advent2024.day4.Day4
import com.grahamedgecombe.advent2024.day5.Day5
import com.grahamedgecombe.advent2024.day6.Day6
import com.grahamedgecombe.advent2024.day7.Day7
import com.grahamedgecombe.advent2024.day8.Day8
import com.grahamedgecombe.advent2024.day9.Day9
import kotlin.time.Duration
import kotlin.time.measureTimedValue

fun main(args: Array<String>) {
    val puzzles = mutableListOf<Puzzle<*>>(
        Day1,
        Day2,
        Day3,
        Day4,
        Day5,
        Day6,
        Day7,
        Day8,
        Day9,
        Day10,
        Day11,
        Day12,
        Day13,
    )

    val day = args.firstOrNull()?.toIntOrNull()
    if (day != null) {
        puzzles.removeIf { puzzle -> puzzle.number != day }
    }

    for (puzzle in puzzles) {
        solve(puzzle)
    }
}

private fun <T> solve(puzzle: Puzzle<T>) {
    val input = measureTimedValue {
        puzzle.parse()
    }

    val solutionPart1 = measureTimedValue {
        puzzle.solvePart1(input.value)
    }
    printSolution(puzzle.number, 1, solutionPart1.value, solutionPart1.duration, input.duration)

    val solutionPart2 = measureTimedValue {
        puzzle.solvePart2(input.value)
    }
    if (solutionPart2.value != null) {
        printSolution(puzzle.number, 2, solutionPart2.value!!, solutionPart2.duration)
    }
}

private fun printSolution(day: Int, part: Int, solution: Any, duration: Duration, parseDuration: Duration? = null) {
    val suffix = if (parseDuration != null) {
        " + $parseDuration parsing"
    } else {
        ""
    }

    val value = solution.toString()
    if (value.contains('\n')) {
        println("Day $day Part $part: ($duration$suffix)")
        println(value.trim())
    } else {
        println("Day $day Part $part: $value ($duration$suffix)")
    }
}
