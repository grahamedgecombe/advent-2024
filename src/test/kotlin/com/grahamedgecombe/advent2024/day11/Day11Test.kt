package com.grahamedgecombe.advent2024.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    @Test
    fun testPart1() {
        assertEquals(55312, Day11.solvePart1(TEST_INPUT))
        assertEquals(187738, Day11.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(223767210249237, Day11.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day11.parse("""
            125 17
        """.trimIndent())
        private val PROD_INPUT = Day11.parse()
    }
}
