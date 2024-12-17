package com.grahamedgecombe.advent2024.day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {
    @Test
    fun testPart1() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", Day17.solvePart1(TEST_INPUT_1))
        assertEquals("7,1,3,4,1,2,6,7,1", Day17.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(117440, Day17.solvePart2(TEST_INPUT_2))
        assertEquals(109019476330651, Day17.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT_1 = Day17.parse("""
            Register A: 729
            Register B: 0
            Register C: 0

            Program: 0,1,5,4,3,0
        """.trimIndent())
        private val TEST_INPUT_2 = Day17.parse("""
            Register A: 2024
            Register B: 0
            Register C: 0

            Program: 0,3,5,4,3,0
        """.trimIndent())
        private val PROD_INPUT = Day17.parse()
    }
}
