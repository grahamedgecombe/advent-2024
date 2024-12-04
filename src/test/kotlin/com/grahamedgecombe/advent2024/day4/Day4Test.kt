package com.grahamedgecombe.advent2024.day4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day4Test {
    @Test
    fun testPart1() {
        assertEquals(18, Day4.solvePart1(TEST_INPUT))
        assertEquals(2646, Day4.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(9, Day4.solvePart2(TEST_INPUT))
        assertEquals(2000, Day4.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day4.parse("""
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent())
        private val PROD_INPUT = Day4.parse()
    }
}
