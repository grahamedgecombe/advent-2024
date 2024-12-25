package com.grahamedgecombe.advent2024.day25

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day25.Day25.Input

object Day25 : Puzzle<Input>(25) {
    data class Input(val locks: List<List<Int>>, val keys: List<List<Int>>)

    private const val WIDTH = 5
    private const val HEIGHT = 7
    private const val AVAILABLE_HEIGHT = HEIGHT - 2

    override fun parse(input: Sequence<String>): Input {
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()

        val lines = mutableListOf<String>()

        val it = input.iterator()
        while (it.hasNext()) {
            val line = it.next()
            if (line.isEmpty()) {
                parse(lines, locks, keys)
                lines.clear()
            } else {
                require(line.length == WIDTH)
                lines += line
            }
        }

        if (lines.isNotEmpty()) {
            parse(lines, locks, keys)
        }

        return Input(locks, keys)
    }

    private fun parse(lines: List<String>, locks: MutableList<List<Int>>, keys: MutableList<List<Int>>) {
        require(lines.isNotEmpty())
        require(lines.size == HEIGHT)

        val topRow = lines.first()
        val bottomRow = lines.last()

        if (topRow.all { it == '#' }) {
            require(bottomRow.all { it == '.' })

            parse(lines, locks)
        } else {
            require(topRow.all { it == '.' } && bottomRow.all { it == '#' })

            parse(lines.reversed(), keys)
        }
    }

    private fun parse(lines: List<String>, output: MutableList<List<Int>>) {
        val heights = mutableListOf<Int>()

        for (col in lines.first().indices) {
            var height = 0

            for (row in 1 until lines.size - 1) {
                if (lines[row][col] == '#') {
                    height++
                } else {
                    require(lines[row][col] == '.')
                    break
                }
            }

            heights += height
        }

        output += heights
    }

    override fun solvePart1(input: Input): Int {
        var count = 0
        for (lock in input.locks) {
            for (key in input.keys) {
                if (!overlaps(lock, key)) {
                    count++
                }
            }
        }
        return count
    }

    private fun overlaps(lock: List<Int>, key: List<Int>): Boolean {
        for ((a, b) in lock.zip(key)) {
            if ((a + b) > AVAILABLE_HEIGHT) {
                return true
            }
        }

        return false
    }
}
