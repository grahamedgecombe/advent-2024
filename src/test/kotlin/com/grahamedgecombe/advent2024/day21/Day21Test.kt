package com.grahamedgecombe.advent2024.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {
    @Test
    fun testPart1() {
        assertEquals(126384, Day21.solvePart1(TEST_INPUT))
        assertEquals(219366, Day21.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day21.parse("""
            029A
            980A
            179A
            456A
            379A
        """.trimIndent())
        private val PROD_INPUT = Day21.parse()
    }
}
