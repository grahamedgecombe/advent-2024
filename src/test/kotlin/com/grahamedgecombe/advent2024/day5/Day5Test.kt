package com.grahamedgecombe.advent2024.day5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day5Test {
    @Test
    fun testPart1() {
        assertEquals(143, Day5.solvePart1(TEST_INPUT))
        assertEquals(5588, Day5.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(123, Day5.solvePart2(TEST_INPUT))
        assertEquals(5331, Day5.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day5.parse("""
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13

            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent())
        private val PROD_INPUT = Day5.parse()
    }
}
