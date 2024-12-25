package com.grahamedgecombe.advent2024.day25

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day25Test {
    @Test
    fun testPart1() {
        assertEquals(3, Day25.solvePart1(TEST_INPUT))
        assertEquals(2854, Day25.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day25.parse("""
            #####
            .####
            .####
            .####
            .#.#.
            .#...
            .....

            #####
            ##.##
            .#.##
            ...##
            ...#.
            ...#.
            .....

            .....
            #....
            #....
            #...#
            #.#.#
            #.###
            #####

            .....
            .....
            #.#..
            ###..
            ###.#
            ###.#
            #####

            .....
            .....
            .....
            #....
            #.#..
            #.#.#
            #####
        """.trimIndent())
        private val PROD_INPUT = Day25.parse()
    }
}
