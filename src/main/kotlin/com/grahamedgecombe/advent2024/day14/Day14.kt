package com.grahamedgecombe.advent2024.day14

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.util.Vector2

object Day14 : Puzzle<List<Day14.Robot>>(14) {
    data class Robot(val position: Vector2, val velocity: Vector2)

    private val REGEX = Regex("p=([0-9]+),([0-9]+) v=(-?[0-9]+),(-?[0-9]+)")

    override fun parse(input: Sequence<String>): List<Robot> {
        return input.map { line ->
            val m = REGEX.matchEntire(line) ?: throw IllegalArgumentException()
            val (px, py, vx, vy) = m.destructured
            Robot(Vector2(px.toInt(), py.toInt()), Vector2(vx.toInt(), vy.toInt()))
        }.toList()
    }

    override fun solvePart1(input: List<Robot>): Int {
        return solve(input, 101, 103)
    }

    fun solve(input: List<Robot>, width: Int, height: Int): Int {
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
