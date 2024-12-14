package com.grahamedgecombe.advent2024.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    @Test
    fun testPart1() {
        assertEquals(12, Day14.solve(TEST_INPUT, 11, 7))
        assertEquals(221142636, Day14.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(7916, Day14.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day14.parse("""
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
        """.trimIndent())
        private val PROD_INPUT = Day14.parse()
    }
}
