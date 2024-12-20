package com.grahamedgecombe.advent2024.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20Test {
    @Test
    fun testPart1() {
        assertEquals(1429, Day20.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day20.parse("""
            ###############
            #...#...#.....#
            #.#.#.#.#.###.#
            #S#...#.#.#...#
            #######.#.#.###
            #######.#.#...#
            #######.#.###.#
            ###..E#...#...#
            ###.#######.###
            #...###...#...#
            #.#####.#.###.#
            #.#...#.#.#...#
            #.#.#.#.#.#.###
            #...#...#...###
            ###############
        """.trimIndent())
        private val PROD_INPUT = Day20.parse()
    }
}
