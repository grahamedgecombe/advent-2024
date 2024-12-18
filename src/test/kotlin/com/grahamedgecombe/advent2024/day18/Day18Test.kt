package com.grahamedgecombe.advent2024.day18

import com.grahamedgecombe.advent2024.util.Vector2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {
    @Test
    fun testPart1() {
        assertEquals(22, Day18.solvePart1(TEST_INPUT, 12, TEST_DESTINATION))
        assertEquals(326, Day18.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals("6,1", Day18.solvePart2(TEST_INPUT, TEST_DESTINATION))
        assertEquals("18,62", Day18.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_DESTINATION = Vector2(6, 6)
        private val TEST_INPUT = Day18.parse("""
            5,4
            4,2
            4,5
            3,0
            2,1
            6,3
            2,4
            1,5
            0,6
            3,3
            2,6
            5,1
            1,2
            5,5
            2,5
            6,5
            1,4
            0,4
            6,4
            1,1
            6,1
            1,0
            0,5
            1,6
            2,0
        """.trimIndent())
        private val PROD_INPUT = Day18.parse()
    }
}
