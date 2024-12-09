package com.grahamedgecombe.advent2024.day9

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {
    @Test
    fun testPart1() {
        assertEquals(1928L, Day9.solvePart1(TEST_INPUT))
        assertEquals(6279058075753, Day9.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(2858L, Day9.solvePart2(TEST_INPUT))
        assertEquals(6301361958738, Day9.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day9.parse("""
            2333133121414131402
        """.trimIndent())
        private val PROD_INPUT = Day9.parse()
    }
}
