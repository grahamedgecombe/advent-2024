package com.grahamedgecombe.advent2024.day24

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day24.Day24.Wire

object Day24 : Puzzle<Map<String, Wire>>(24) {
    private val CONST_REGEX = Regex("([a-z0-9]+): ([01])")
    private val GATE_REGEX = Regex("([a-z0-9]+) (AND|OR|XOR) ([a-z0-9]+) -> ([a-z0-9]+)")

    enum class Op {
        AND, OR, XOR
    }
    sealed interface Wire
    data class Const(val value: Boolean) : Wire
    data class Gate(val left: String, val op: Op, val right: String) : Wire

    override fun parse(input: Sequence<String>): Map<String, Wire> {
        val wires = mutableMapOf<String, Wire>()

        for (line in input) {
            if (line.isEmpty()) {
                continue
            }

            var m = CONST_REGEX.matchEntire(line)
            if (m != null) {
                val (dest, value) = m.destructured
                wires[dest] = Const(value == "1")
                continue
            }

            m = GATE_REGEX.matchEntire(line)
            if (m != null) {
                val (left, op, right, dest) = m.destructured
                wires[dest] = Gate(left, Op.valueOf(op), right)
                continue
            }

            throw IllegalArgumentException()
        }

        return wires
    }

    private fun outputWires(input: Map<String, Wire>) = sequence {
        var i = 0

        while (true) {
            val wire = String.format("z%02d", i)
            if (wire !in input) {
                break
            }
            yield(wire)
            i++
        }
    }

    override fun solvePart1(input: Map<String, Wire>): Long {
        var result = 0L
        for ((i, wire) in outputWires(input).withIndex()) {
            if (evaluate(input, wire)) {
                result = result or (1L shl i)
            }
        }
        return result
    }

    private fun evaluate(wires: Map<String, Wire>, name: String): Boolean {
        val wire = wires[name] ?: throw IllegalArgumentException()

        return when (wire) {
            is Const -> wire.value
            is Gate -> {
                val left = evaluate(wires, wire.left)
                val right = evaluate(wires, wire.right)

                val value = when (wire.op) {
                    Op.AND -> left && right
                    Op.OR -> left || right
                    Op.XOR -> left != right
                }

                value
            }
        }
    }
}
