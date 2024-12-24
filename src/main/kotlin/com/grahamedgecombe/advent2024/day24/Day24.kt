package com.grahamedgecombe.advent2024.day24

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.day24.Day24.Wire

object Day24 : Puzzle<Map<String, Wire>>(24) {
    private val CONST_REGEX = Regex("([a-z0-9]+): ([01])")
    private val GATE_REGEX = Regex("([a-z0-9]+) (AND|OR|XOR) ([a-z0-9]+) -> ([a-z0-9]+)")

    enum class Op {
        AND, OR, XOR
    }
    sealed interface Wire {
        var name: String

        fun toExpr(wires: Map<String, Wire>): Expr
    }
    data class Const(override var name: String, val value: Boolean) : Wire {
        override fun toExpr(wires: Map<String, Wire>): Expr {
            return Input(name)
        }
    }

    data class Gate(override var name: String, val left: String, val op: Op, val right: String) : Wire {
        override fun toExpr(wires: Map<String, Wire>): Expr {
            return BinOp(wires[left]!!.toExpr(wires), op, wires[right]!!.toExpr(wires))
        }
    }

    override fun parse(input: Sequence<String>): Map<String, Wire> {
        val wires = mutableMapOf<String, Wire>()

        for (line in input) {
            if (line.isEmpty()) {
                continue
            }

            var m = CONST_REGEX.matchEntire(line)
            if (m != null) {
                val (dest, value) = m.destructured
                wires[dest] = Const(dest, value == "1")
                continue
            }

            m = GATE_REGEX.matchEntire(line)
            if (m != null) {
                val (left, op, right, dest) = m.destructured
                wires[dest] = Gate(dest, left, Op.valueOf(op), right)
                continue
            }

            throw IllegalArgumentException()
        }

        return wires
    }

    private fun wireSequence(input: Map<String, Wire>, prefix: Char) = sequence {
        var i = 0

        while (true) {
            val name = String.format("%c%02d", prefix, i)

            val wire = input[name] ?: break
            yield(wire)

            i++
        }
    }

    override fun solvePart1(input: Map<String, Wire>): Long {
        var result = 0L
        for ((i, wire) in wireSequence(input, 'z').withIndex()) {
            if (evaluate(input, wire.name)) {
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

    sealed interface Expr
    data class Input(val name: String) : Expr
    data class BinOp(val left: Expr, val op: Op, val right: Expr) : Expr {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as BinOp

            if (op != other.op) return false

            if (left == other.left && right == other.right) return true
            if (left == other.right && right == other.left) return true

            return false
        }

        override fun hashCode(): Int {
            var result = left.hashCode() xor right.hashCode()
            result = 31 * result + op.hashCode()
            return result
        }
    }

    override fun solvePart2(input: Map<String, Wire>): String {
        val wires = input.toMutableMap()
        val swapped = mutableSetOf<String>()

        val xs = wireSequence(wires, 'x').toList()
        val ys = wireSequence(wires, 'y').toList()
        val zs = wireSequence(wires, 'z').toList()
        require(xs.isNotEmpty() && xs.size == ys.size && (ys.size + 1) == zs.size)

        /*
         * Build the correct expression for each output, then recurse into the
         * input expression to find wires we should swap.
         */
        var carry: Expr? = null

        for (i in xs.indices) {
            val x = xs[i]
            val y = ys[i]
            val z = zs[i]

            val xor = BinOp(Input(x.name), Op.XOR, Input(y.name))
            val and = BinOp(Input(x.name), Op.AND, Input(y.name))

            if (carry == null) {
                // half adder
                fix(wires, z, xor, swapped)
                carry = BinOp(Input(x.name), Op.AND, Input(y.name))
            } else {
                // full adder
                fix(wires, z, BinOp(xor, Op.XOR, carry), swapped)
                carry = BinOp(and, Op.OR, BinOp(carry, Op.AND, xor))
            }
        }

        // carry out
        check(carry != null)
        fix(wires, zs.last(), carry, swapped)

        return swapped.sorted().joinToString(",")
    }

    private fun fix(wires: MutableMap<String, Wire>, actualWire: Wire, expectedExpr: Expr, swapped: MutableSet<String>) {
        when (expectedExpr) {
            is Input -> require(actualWire is Const && actualWire.name == expectedExpr.name)
            is BinOp -> {
                /*
                 * Check if actual and expected expression match. Return if so
                 * as the circuit is correct.
                 */
                var actualExpr = actualWire.toExpr(wires)
                if (actualExpr == expectedExpr) {
                    return
                }

                /*
                 * If one argument is correct, recurse into the other argument
                 * to attempt to fix it.
                 *
                 * We can't do this if both arguments are incorrect, as we
                 * don't know whether the actual left argument matches to the
                 * expected left or right argument, and similar for the actual
                 * right argument.
                 *
                 * If both arguments are correct, this is caught by the case
                 * above.
                 */
                if (actualWire is Gate && actualWire.op == expectedExpr.op) {
                    val leftWire = wires[actualWire.left]!!
                    val rightWire = wires[actualWire.right]!!

                    val leftExpr = leftWire.toExpr(wires)
                    val rightExpr = rightWire.toExpr(wires)

                    if (leftExpr == expectedExpr.left) {
                        fix(wires, rightWire, expectedExpr.right, swapped)
                    } else if (leftExpr == expectedExpr.right) {
                        fix(wires, rightWire, expectedExpr.left, swapped)
                    } else if (rightExpr == expectedExpr.left) {
                        fix(wires, leftWire, expectedExpr.right, swapped)
                    } else if (rightExpr == expectedExpr.right) {
                        fix(wires, leftWire, expectedExpr.left, swapped)
                    } else {
                        throw UnsolvableException()
                    }
                }

                /*
                 * Check if actual and expected expression match now we've
                 * recursed. Return if so as the circuit is correct.
                 */
                actualExpr = actualWire.toExpr(wires)
                if (actualExpr == expectedExpr) {
                    return
                }

                /*
                 * Find a candidate wire whose actual expression matches our
                 * expected expression, then swap the actual and candidate wire
                 * to fix the circuit.
                 */
                for (candidateWire in wires.values) {
                    if (candidateWire.toExpr(wires) == expectedExpr) {
                        swap(wires, candidateWire, actualWire, swapped)
                        return
                    }
                }

                throw UnsolvableException()
            }
        }
    }

    private fun swap(wires: MutableMap<String, Wire>, a: Wire, b: Wire, swapped: MutableSet<String>) {
        require(a.name != b.name)

        val temp = a.name
        a.name = b.name
        b.name = temp

        wires[a.name] = a
        wires[b.name] = b

        swapped += a.name
        swapped += b.name
    }
}
