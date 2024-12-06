package com.grahamedgecombe.advent2024.day6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day6Test {
    @Test
    fun testPart1() {
        assertEquals(41, Day6.solvePart1(TEST_INPUT))
        assertEquals(4663, Day6.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day6.parse("""
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent())
        private val PROD_INPUT = Day6.parse()
    }
}
