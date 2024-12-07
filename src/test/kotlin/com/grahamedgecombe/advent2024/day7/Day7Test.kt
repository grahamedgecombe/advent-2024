package com.grahamedgecombe.advent2024.day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {
    @Test
    fun testPart1() {
        assertEquals(3749, Day7.solvePart1(TEST_INPUT))
        assertEquals(1545311493300, Day7.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(11387, Day7.solvePart2(TEST_INPUT))
        assertEquals(169122112716571, Day7.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day7.parse("""
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent())
        private val PROD_INPUT = Day7.parse()
    }
}
