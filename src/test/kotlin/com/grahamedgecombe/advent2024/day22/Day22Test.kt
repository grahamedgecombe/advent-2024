package com.grahamedgecombe.advent2024.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {
    @Test
    fun testPart1() {
        assertEquals(37327623, Day22.solvePart1(TEST_INPUT))
        assertEquals(13764677935, Day22.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day22.parse("""
            1
            10
            100
            2024
        """.trimIndent())
        private val PROD_INPUT = Day22.parse()
    }
}
