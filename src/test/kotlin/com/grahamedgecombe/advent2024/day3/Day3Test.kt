package com.grahamedgecombe.advent2024.day3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day3Test {
    @Test
    fun testPart1() {
        assertEquals(161, Day3.solvePart1(TEST_INPUT_1))
        assertEquals(179571322, Day3.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(48, Day3.solvePart2(TEST_INPUT_2))
        assertEquals(103811193, Day3.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT_1 = Day3.parse("""
            xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
        """.trimIndent())
        private val TEST_INPUT_2 = Day3.parse("""
            xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
        """.trimIndent())
        private val PROD_INPUT = Day3.parse()
    }
}
