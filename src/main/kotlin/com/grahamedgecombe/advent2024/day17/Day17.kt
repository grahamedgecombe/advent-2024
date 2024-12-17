package com.grahamedgecombe.advent2024.day17

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.UnsolvableException
import com.grahamedgecombe.advent2024.day17.Day17.Input

object Day17 : Puzzle<Input>(17) {
    data class Input(
        val a: Long,
        val b: Long,
        val c: Long,
        val program: List<Int>,
    )

    class Computer(
        private var a: Long,
        private var b: Long,
        private var c: Long,
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
                    OPCODE_ADV -> a = a shr combo(operand).toInt()
                    OPCODE_BXL -> b = b xor operand.toLong()
                    OPCODE_BST -> b = combo(operand) and 7
                    OPCODE_JNZ -> if (a != 0L) {
                        ip = operand
                    }
                    OPCODE_BXC -> b = b xor c
                    OPCODE_OUT -> yield(combo(operand).toInt() and 7)
                    OPCODE_BDV -> b = a shr combo(operand).toInt()
                    OPCODE_CDV -> c = a shr combo(operand).toInt()
                    else -> throw UnsolvableException()
                }
            }
        }

        private fun combo(operand: Int): Long {
            return when (operand) {
                OPERAND_0, OPERAND_1, OPERAND_2, OPERAND_3 -> operand.toLong()
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
        var a = 0L
        var b = 0L
        var c = 0L

        val it = input.iterator()
        while (it.hasNext()) {
            val m = REGISTER_REGEX.matchEntire(it.next()) ?: break

            val (reg, value) = m.destructured
            when (reg) {
                "A" -> a = value.toLong()
                "B" -> b = value.toLong()
                "C" -> c = value.toLong()
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

    override fun solvePart2(input: Input): Long {
        /*
         * Decompiled version of my input:
         *
         *     do {
         *         b = a % 8
         *         b = b xor 5
         *         c = a shr b
         *         a = a / 8
         *         b = b xor c
         *         b = b xor 6
         *         yield(b % 8)
         *     } while (a != 0)
         *
         * This is a bit like itoa(), except with some extra XOR-based
         * encryption on top. The tricky part is that while we mostly operate
         * on 3 bits of A at a time, C can use up to 11 bits of A - meaning
         * more than one input could produce the same output digit in each
         * position.
         *
         * So rather than considering one possible value of B for each output
         * digit, we have to support multiple possibilities with recursion.
         */
        return solve(input.program, input.program.reversed(), 0)
    }

    private fun solve(program: List<Int>, expected: List<Int>, a: Long): Long {
        if (expected.isEmpty()) {
            return a
        }

        val head = expected.first()
        val tail = expected.slice(1 until expected.size)
        var min = Long.MAX_VALUE

        for (digit in 0..7) {
            val nextA = a * 8 + digit
            val output = Computer(nextA, 0, 0, program).evaluate().first()
            if (output == head) {
                min = minOf(min, solve(program, tail, nextA))
            }
        }

        return min
    }
}
