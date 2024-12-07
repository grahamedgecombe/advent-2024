package com.grahamedgecombe.advent2024.day7

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day7.Day7.Equation

object Day7 : Puzzle<List<Equation>>(7) {
    data class Equation(val testValue: Long, val numbers: List<Long>) {
        fun isTrue(): Boolean {
            return isTrue(numbers.first(), numbers.slice(1 until numbers.size))
        }

        private fun isTrue(value: Long, numbers: List<Long>): Boolean {
            if (value > testValue) {
                return false
            } else if (numbers.isEmpty()) {
                return value == testValue
            }

            val head = numbers.first()
            val tail = numbers.slice(1 until numbers.size)

            return isTrue(value + head, tail) || isTrue(value * head, tail)
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
        return input.filter(Equation::isTrue).sumOf(Equation::testValue)
    }
}
