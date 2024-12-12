package com.grahamedgecombe.advent2024.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun testPart1() {
        assertEquals(1930, Day12.solvePart1(TEST_INPUT))
        assertEquals(1424006, Day12.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(1206, Day12.solvePart2(TEST_INPUT))
        assertEquals(858684, Day12.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day12.parse("""
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent())
        private val PROD_INPUT = Day12.parse()
    }
}
