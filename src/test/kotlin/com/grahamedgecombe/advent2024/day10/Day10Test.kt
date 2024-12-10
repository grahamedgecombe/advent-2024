package com.grahamedgecombe.advent2024.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun testPart1() {
        assertEquals(36, Day10.solvePart1(TEST_INPUT))
        assertEquals(510, Day10.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(81, Day10.solvePart2(TEST_INPUT))
        assertEquals(1058, Day10.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day10.parse("""
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
        """.trimIndent())
        private val PROD_INPUT = Day10.parse()
    }
}
