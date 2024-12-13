package com.grahamedgecombe.advent2024.day13

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day13.Day13.Machine
import com.grahamedgecombe.advent2024.util.Vector2l

object Day13 : Puzzle<List<Machine>>(13) {
    data class Machine(
        val a: Vector2l,
        val b: Vector2l,
        val prize: Vector2l,
    ) {
        fun transform(): Machine {
            return Machine(a, b, prize + Vector2l(10000000000000, 10000000000000))
        }

        fun solve(): Pair<Long, Long>? {
            val d = a.y * b.x - a.x * b.y
            if (d == 0L) {
                return null
            }

            val bTimes = (a.y * prize.x - a.x * prize.y) / d
            val aTimes = (prize.x - b.x * bTimes) / a.x

            if (aTimes < 0 || bTimes < 0 || a * aTimes + b * bTimes != prize) {
                return null
            }

            return Pair(aTimes, bTimes)
        }
    }

    private val BUTTON_REGEX = Regex("Button [AB]: X\\+([0-9]+), Y\\+([0-9]+)")
    private val PRIZE_REGEX = Regex("Prize: X=([0-9]+), Y=([0-9]+)")

    override fun parse(input: Sequence<String>): List<Machine> {
        return buildList {
            val it = input.iterator()

            while (it.hasNext()) {
                val (aX, aY) = BUTTON_REGEX.matchEntire(it.next())?.destructured ?: throw IllegalArgumentException()
                val (bX, bY) = BUTTON_REGEX.matchEntire(it.next())?.destructured ?: throw IllegalArgumentException()
                val (prizeX, prizeY) = PRIZE_REGEX.matchEntire(it.next())?.destructured ?: throw IllegalArgumentException()

                add(Machine(
                    Vector2l(aX.toLong(), aY.toLong()),
                    Vector2l(bX.toLong(), bY.toLong()),
                    Vector2l(prizeX.toLong(), prizeY.toLong()),
                ))

                if (it.hasNext()) {
                    require(it.next().isEmpty())
                }
            }
        }
    }

    override fun solvePart1(input: List<Machine>): Long {
        var sum = 0L

        for (machine in input) {
            val (a, b) = machine.solve() ?: continue
            if (a <= 100 && b <= 100) {
                sum += tokens(a, b)
            }
        }

        return sum
    }

    override fun solvePart2(input: List<Machine>): Long {
        var sum = 0L

        for (machine in input) {
            val (a, b) = machine.transform().solve() ?: continue
            sum += tokens(a, b)
        }

        return sum
    }

    private fun tokens(a: Long, b: Long): Long {
        return a * 3 + b
    }
}
