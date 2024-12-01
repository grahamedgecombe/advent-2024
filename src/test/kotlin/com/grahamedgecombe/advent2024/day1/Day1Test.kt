package com.grahamedgecombe.advent2024.day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun testPart1() {
        assertEquals(11, Day1.solvePart1(TEST_INPUT))
        assertEquals(1889772, Day1.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day1.parse("""
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent())
        private val PROD_INPUT = Day1.parse()
    }
}
