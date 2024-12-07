package com.grahamedgecombe.advent2024.day7

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day7.Day7.Equation

object Day7 : Puzzle<List<Equation>>(7) {
    data class Equation(val testValue: Long, val numbers: List<Long>) {
        fun isTruePart1(): Boolean {
            return isTrue(false, numbers.first(), numbers.slice(1 until numbers.size))
        }

        fun isTruePart2(): Boolean {
            return isTrue(true, numbers.first(), numbers.slice(1 until numbers.size))
        }

        private fun isTrue(part2: Boolean, value: Long, numbers: List<Long>): Boolean {
            if (value > testValue) {
                return false
            } else if (numbers.isEmpty()) {
                return value == testValue
            }

            val head = numbers.first()
            val tail = numbers.slice(1 until numbers.size)

            return isTrue(part2, value + head, tail) || isTrue(part2, value * head, tail) ||
                (part2 && isTrue(part2, value concat head, tail))
        }

        private infix fun Long.concat(other: Long): Long {
            return (toString() + other.toString()).toLong()
        }

        companion object {
            fun parse(line: String): Equation {
                val (testValue, numbers) = line.split(": ", limit = 2)
                return Equation(testValue.toLong(), numbers.split(' ').map(String::toLong))
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Equation> {
        return input.map(Equation::parse).toList()
    }

    override fun solvePart1(input: List<Equation>): Long {
        return input.filter(Equation::isTruePart1).sumOf(Equation::testValue)
    }

    override fun solvePart2(input: List<Equation>): Long {
        return input.filter(Equation::isTruePart2).sumOf(Equation::testValue)
    }
}
