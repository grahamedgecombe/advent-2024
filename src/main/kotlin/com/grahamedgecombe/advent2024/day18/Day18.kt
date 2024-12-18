package com.grahamedgecombe.advent2024.day18

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.util.Bfs
import com.grahamedgecombe.advent2024.util.Vector2

object Day18 : Puzzle<List<Vector2>>(18) {
    private val DIRECTIONS = mutableSetOf(
        Vector2(-1, 0),
        Vector2(1, 0),
        Vector2(0, -1),
        Vector2(0, 1),
    )
    private val ORIGIN = Vector2(0, 0)
    private val DESTINATION = Vector2(70, 70)

    override fun parse(input: Sequence<String>): List<Vector2> {
        return input.map { line ->
            val (x, y) = line.split(',', limit = 2)
            Vector2(x.toInt(), y.toInt())
        }.toList()
    }

    override fun solvePart1(input: List<Vector2>): Int {
        return solvePart1(input, 1024, DESTINATION)
    }

    data class Node(
        val blocked: Set<Vector2>,
        val destination: Vector2,
        val position: Vector2,
    ) : Bfs.Node<Node> {
        override val isGoal: Boolean
            get() = position == destination

        override val neighbours: Sequence<Node>
            get() = sequence {
                for (direction in DIRECTIONS) {
                    val v = position + direction
                    if (v in blocked || v.x !in 0..destination.x || v.y !in 0..destination.y) {
                        continue
                    }
                    yield(Node(blocked, destination, v))
                }
            }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            return position == other.position
        }

        override fun hashCode(): Int {
            return position.hashCode()
        }
    }

    fun solvePart1(input: List<Vector2>, n: Int, destination: Vector2): Int {
        val path = Bfs.search(Node(input.slice(0 until n).toSet(), destination, ORIGIN))
            .firstOrNull() ?: throw UnsolvableException()
        return path.size - 1
    }

    override fun solvePart2(input: List<Vector2>): String {
        return solvePart2(input, DESTINATION)
    }

    fun solvePart2(input: List<Vector2>, destination: Vector2): String {
        for (i in 0 until input.size) {
            if (Bfs.search(Node(input.slice(0 until i).toSet(), destination, ORIGIN)).none()) {
                return input[i - 1].toString()
            }
        }

        throw UnsolvableException()
    }
}
