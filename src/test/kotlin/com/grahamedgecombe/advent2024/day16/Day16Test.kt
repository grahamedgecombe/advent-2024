package com.grahamedgecombe.advent2024.day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun testPart1() {
        assertEquals(7036, Day16.solvePart1(TEST_INPUT_1))
        assertEquals(11048, Day16.solvePart1(TEST_INPUT_2))
        assertEquals(133584, Day16.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(45, Day16.solvePart2(TEST_INPUT_1))
        assertEquals(64, Day16.solvePart2(TEST_INPUT_2))
        assertEquals(622, Day16.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT_1 = Day16.parse("""
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent())
        private val TEST_INPUT_2 = Day16.parse("""
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
        """.trimIndent())
        private val PROD_INPUT = Day16.parse()
    }
}
