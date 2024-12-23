package com.grahamedgecombe.advent2024.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day23Test {
    @Test
    fun testPart1() {
        assertEquals(7, Day23.solvePart1(TEST_INPUT))
        assertEquals(1098, Day23.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day23.parse("""
            kh-tc
            qp-kh
            de-cg
            ka-co
            yn-aq
            qp-ub
            cg-tb
            vc-aq
            tb-ka
            wh-tc
            yn-cg
            kh-ub
            ta-co
            de-co
            tc-td
            tb-wq
            wh-td
            ta-ka
            td-qp
            aq-cg
            wq-ub
            ub-vc
            de-ta
            wq-aq
            wq-vc
            wh-yn
            ka-de
            kh-ta
            co-tc
            wh-qp
            tb-vc
            td-yn
        """.trimIndent())
        private val PROD_INPUT = Day23.parse()
    }
}
