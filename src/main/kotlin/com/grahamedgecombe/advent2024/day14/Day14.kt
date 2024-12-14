package com.grahamedgecombe.advent2024.day14

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.util.Vector2
import kotlin.math.abs

object Day14 : Puzzle<List<Day14.Robot>>(14) {
    data class Robot(val position: Vector2, val velocity: Vector2)

    private const val WIDTH = 101
    private const val HEIGHT = 103
    private val REGEX = Regex("p=([0-9]+),([0-9]+) v=(-?[0-9]+),(-?[0-9]+)")

    override fun parse(input: Sequence<String>): List<Robot> {
        return input.map { line ->
            val m = REGEX.matchEntire(line) ?: throw IllegalArgumentException()
            val (px, py, vx, vy) = m.destructured
            Robot(Vector2(px.toInt(), py.toInt()), Vector2(vx.toInt(), vy.toInt()))
        }.toList()
    }

    override fun solvePart1(input: List<Robot>): Int {
        return solve(input, WIDTH, HEIGHT)
    }

    override fun solvePart2(input: List<Robot>): Int {
        for (n in 0 until WIDTH * HEIGHT) {
            val positions = mutableSetOf<Vector2>()

            for (robot in input) {
                val position = robot.position + robot.velocity * n

                val x = ((position.x % WIDTH) + WIDTH) % WIDTH
                val y = ((position.y % HEIGHT) + HEIGHT) % HEIGHT

                positions += Vector2(x, y)
            }

            var score = 0

            for (a in positions) {
                for (b in positions) {
                    if (a.x == b.x && abs(a.y - b.y) == 1) {
                        score++
                    } else if (a.y == b.y && abs(a.x - b.x) == 1) {
                        score++
                    }
                }
            }

            if (score >= 300) {
                return n
            }
        }

        throw UnsolvableException()
    }

    internal fun solve(input: List<Robot>, width: Int, height: Int): Int {
        var topLeft = 0
        var topRight = 0
        var bottomLeft = 0
        var bottomRight = 0

        for (robot in input) {
            val position = robot.position + robot.velocity * 100

            val x = ((position.x % width) + width) % width
            val y = ((position.y % height) + height) % height

            val left = x < width / 2
            val right = x >= (width + 1) / 2

            val top = y < height / 2
            val bottom = y >= (height + 1) / 2

            when {
                top && left -> topLeft++
                top && right -> topRight++
                bottom && left -> bottomLeft++
                bottom && right -> bottomRight++
            }
        }

        return topLeft * topRight * bottomLeft * bottomRight
    }
}
