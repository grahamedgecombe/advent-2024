package com.grahamedgecombe.advent2024.day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun testPart1() {
        assertEquals(480, Day13.solvePart1(TEST_INPUT))
        assertEquals(35997, Day13.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(82510994362072, Day13.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day13.parse("""
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400

            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176

            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450

            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent())
        private val PROD_INPUT = Day13.parse()
    }
}
