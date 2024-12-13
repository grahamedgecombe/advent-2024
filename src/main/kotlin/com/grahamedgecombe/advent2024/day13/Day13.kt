package com.grahamedgecombe.advent2024.day13

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day13.Day13.Machine
import com.grahamedgecombe.advent2024.util.Vector2

object Day13 : Puzzle<List<Machine>>(13) {
    data class Machine(
        val a: Vector2,
        val b: Vector2,
        val prize: Vector2,
    )

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
                    Vector2(aX.toInt(), aY.toInt()),
                    Vector2(bX.toInt(), bY.toInt()),
                    Vector2(prizeX.toInt(), prizeY.toInt()),
                ))

                if (it.hasNext()) {
                    require(it.next().isEmpty())
                }
            }
        }
    }

    override fun solvePart1(input: List<Machine>): Int {
        var sum = 0

        for (machine in input) {
            val d = machine.a.y * machine.b.x - machine.a.x * machine.b.y
            if (d == 0) {
                continue
            }

            val b = (machine.a.y * machine.prize.x - machine.a.x * machine.prize.y) / d
            val a = (machine.prize.x - machine.b.x * b) / machine.a.x

            if (a <= 100 && b <= 100) {
                sum += a * 3 + b
            }
        }

        return sum
    }
}
