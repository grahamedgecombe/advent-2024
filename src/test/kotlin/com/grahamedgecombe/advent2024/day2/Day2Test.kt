package com.grahamedgecombe.advent2024.day2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2Test {
    @Test
    fun testPart1() {
        assertEquals(2, Day2.solvePart1(TEST_INPUT))
        assertEquals(680, Day2.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(4, Day2.solvePart2(TEST_INPUT))
        assertEquals(710, Day2.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day2.parse("""
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
        """.trimIndent())
        private val PROD_INPUT = Day2.parse()
    }
}
