package com.grahamedgecombe.advent2024.day16

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.util.Dijkstra
import com.grahamedgecombe.advent2024.util.CharGrid
import com.grahamedgecombe.advent2024.util.Vector2

object Day16 : Puzzle<CharGrid>(16) {
    private val EAST = Vector2(1, 0)

    data class Node(val grid: CharGrid, val position: Vector2, val direction: Vector2) : Dijkstra.Node<Node> {
        override val isGoal: Boolean
            get() = grid[position] == 'E'

        override val neighbours: Sequence<Dijkstra.Neighbour<Node>>
            get() = sequence {
                yield(Dijkstra.Neighbour(Node(grid, position, Vector2(-direction.y, direction.x)), 1000))
                yield(Dijkstra.Neighbour(Node(grid, position, Vector2(direction.y, -direction.x)), 1000))

                if (grid[position + direction] != '#') {
                    yield(Dijkstra.Neighbour(Node(grid, position + direction, direction), 1))
                }
            }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (position != other.position) return false
            if (direction != other.direction) return false

            return true
        }

        override fun hashCode(): Int {
            var result = position.hashCode()
            result = 31 * result + direction.hashCode()
            return result
        }
    }

    override fun parse(input: Sequence<String>): CharGrid {
        return CharGrid.parse(input.toList(), '#')
    }

    override fun solvePart1(input: CharGrid): Int {
        val start = input.find('S') ?: throw UnsolvableException()
        val path = Dijkstra.search(Node(input, start, EAST)).firstOrNull() ?: throw UnsolvableException()
        return path.distance
    }

    override fun solvePart2(input: CharGrid): Int {
        val start = input.find('S') ?: throw UnsolvableException()
        val tiles = mutableSetOf<Vector2>()
        var best: Int? = null

        for (path in Dijkstra.search(Node(input, start, EAST))) {
            if (best == null) {
                best = path.distance
            }

            if (best == path.distance) {
                for (node in path.nodes) {
                    tiles += node.position
                }
            }
        }

        return tiles.size
    }
}
