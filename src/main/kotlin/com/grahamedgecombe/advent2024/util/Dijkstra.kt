package com.grahamedgecombe.advent2024.util

import java.util.PriorityQueue

object Dijkstra {
    interface Node<T : Node<T>> {
        val isGoal: Boolean
        val neighbours: Sequence<Neighbour<T>>
    }

    data class Neighbour<T : Node<T>>(val node: T, val length: Int)
    data class Path<T : Node<T>>(val nodes: List<T>, val distance: Int)

    fun <T : Node<T>> search(vararg roots: T): Sequence<Path<T>> {
        return search(roots.asIterable())
    }

    fun <T : Node<T>> search(roots: Iterable<T>): Sequence<Path<T>> {
        val distances = mutableMapOf<T, Int>()
        val queue = PriorityQueue(compareBy(distances::get))
        val parents = mutableMapOf<T, MutableSet<T>>()

        for (root in roots) {
            distances[root] = 0
            queue += root
        }

        return sequence {
            while (true) {
                val current = queue.poll() ?: break
                if (current.isGoal) {
                    yieldAll(paths(parents, listOf(current), current, distances[current]!!))
                }

                for ((neighbour, length) in current.neighbours) {
                    val alt = distances[current]!! + length
                    val distance = distances.getOrDefault(neighbour, Int.MAX_VALUE)
                    if (alt > distance) {
                        continue
                    }

                    /*
                     * TODO(gpe): find an efficient way to remove an item
                     * from a PriorityQueue to reduce the number of nodes
                     * we have to visit.
                     */
                    queue += neighbour

                    if (alt < distance) {
                        distances[neighbour] = alt
                        parents[neighbour] = mutableSetOf(current)
                    } else {
                        parents.getOrPut(neighbour, ::mutableSetOf) += current
                    }
                }
            }
        }
    }

    private fun <T : Node<T>> paths(parents: Map<T, Set<T>>, path: List<T>, node: T, distance: Int): Sequence<Path<T>> = sequence {
        val nodes = parents[node]
        if (nodes == null || nodes.isEmpty()) {
            yield(Path(path.reversed(), distance))
            return@sequence
        }

        for (parent in nodes) {
            yieldAll(paths(parents, path + parent, parent, distance))
        }
    }
}
