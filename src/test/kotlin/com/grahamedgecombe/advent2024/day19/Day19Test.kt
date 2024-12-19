package com.grahamedgecombe.advent2024.day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day19Test {
    @Test
    fun testPart1() {
        assertEquals(6, Day19.solvePart1(TEST_INPUT))
        assertEquals(272, Day19.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day19.parse("""
            r, wr, b, g, bwu, rb, gb, br

            brwrr
            bggr
            gbbr
            rrbgbr
            ubwu
            bwurrg
            brgr
            bbrgwb
        """.trimIndent())
        private val PROD_INPUT = Day19.parse()
    }
}
