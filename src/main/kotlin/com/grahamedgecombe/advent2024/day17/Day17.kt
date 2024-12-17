package com.grahamedgecombe.advent2024.day17

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.day17.Day17.Input

object Day17 : Puzzle<Input>(17) {
    data class Input(
        val a: Int,
        val b: Int,
        val c: Int,
        val program: List<Int>,
    )

    class Computer private constructor(
        private var a: Int,
        private var b: Int,
        private var c: Int,
        private val program: List<Int>,
    ) {
        constructor(input: Input) : this(input.a, input.b, input.c, input.program)

        private var ip = 0

        fun evaluate() = sequence {
            while (ip < program.size - 1) {
                val opcode = program[ip]
                val operand = program[ip + 1]

                ip += 2

                when (opcode) {
                    OPCODE_ADV -> a = a shr combo(operand)
                    OPCODE_BXL -> b = b xor operand
                    OPCODE_BST -> b = combo(operand) and 7
                    OPCODE_JNZ -> if (a != 0) {
                        ip = operand
                    }
                    OPCODE_BXC -> b = b xor c
                    OPCODE_OUT -> yield(combo(operand) and 7)
                    OPCODE_BDV -> b = a shr combo(operand)
                    OPCODE_CDV -> c = a shr combo(operand)
                    else -> throw UnsolvableException()
                }
            }
        }

        private fun combo(operand: Int): Int {
            return when (operand) {
                OPERAND_0, OPERAND_1, OPERAND_2, OPERAND_3 -> operand
                OPERAND_A -> a
                OPERAND_B -> b
                OPERAND_C -> c
                else -> throw UnsolvableException()
            }
        }

        private companion object {
            private const val OPCODE_ADV = 0
            private const val OPCODE_BXL = 1
            private const val OPCODE_BST = 2
            private const val OPCODE_JNZ = 3
            private const val OPCODE_BXC = 4
            private const val OPCODE_OUT = 5
            private const val OPCODE_BDV = 6
            private const val OPCODE_CDV = 7

            private const val OPERAND_0 = 0
            private const val OPERAND_1 = 1
            private const val OPERAND_2 = 2
            private const val OPERAND_3 = 3
            private const val OPERAND_A = 4
            private const val OPERAND_B = 5
            private const val OPERAND_C = 6
        }
    }

    private val REGISTER_REGEX = Regex("Register ([ABC]): ([0-9]+)")
    private const val PROGRAM_PREFIX = "Program: "

    override fun parse(input: Sequence<String>): Input {
        var a = 0
        var b = 0
        var c = 0

        val it = input.iterator()
        while (it.hasNext()) {
            val m = REGISTER_REGEX.matchEntire(it.next()) ?: break

            val (reg, value) = m.destructured
            when (reg) {
                "A" -> a = value.toInt()
                "B" -> b = value.toInt()
                "C" -> c = value.toInt()
            }
        }

        require(it.hasNext())
        val line = it.next()

        require(line.startsWith(PROGRAM_PREFIX))

        val program = line.substring(PROGRAM_PREFIX.length)
            .split(',')
            .map(String::toInt)
            .toList()

        require(!it.hasNext())

        return Input(a, b, c, program)
    }

    override fun solvePart1(input: Input): String {
        return Computer(input).evaluate()
            .map(Int::toString)
            .joinToString(",")
    }
}
