package com.grahamedgecombe.advent2024.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {
    @Test
    fun testPart1() {
        assertEquals(37327623, Day22.solvePart1(TEST_INPUT_1))
        assertEquals(13764677935, Day22.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(23, Day22.solvePart2(TEST_INPUT_2))
        assertEquals(1619, Day22.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT_1 = Day22.parse("""
            1
            10
            100
            2024
        """.trimIndent())
        private val TEST_INPUT_2 = Day22.parse("""
            1
            2
            3
            2024
        """.trimIndent())
        private val PROD_INPUT = Day22.parse()
    }
}
