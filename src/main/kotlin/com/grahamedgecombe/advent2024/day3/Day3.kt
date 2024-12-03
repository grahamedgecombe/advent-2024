package com.grahamedgecombe.advent2024.day3

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day3.Day3.Insn

object Day3 : Puzzle<List<Insn>>(3) {
    sealed interface Insn {
        object Enable : Insn
        object Disable : Insn
        data class Mul(val a: Int, val b: Int) : Insn
    }

    private val REGEX = Regex("(do|don't|mul)\\((?:([0-9]{1,3}),([0-9]{1,3}))?\\)")

    override fun parse(input: Sequence<String>): List<Insn> {
        return buildList {
            for (line in input) {
                for (match in REGEX.findAll(line)) {
                    val (type, a, b) = match.destructured

                    when (type) {
                        "do" -> add(Insn.Enable)
                        "don't" -> add(Insn.Disable)
                        "mul" -> add(Insn.Mul(a.toInt(), b.toInt()))
                    }
                }
            }
        }
    }

    override fun solvePart1(input: List<Insn>): Int {
        return input.filterIsInstance<Insn.Mul>().sumOf { (a, b) -> a * b }
    }

    override fun solvePart2(input: List<Insn>): Int {
        var sum = 0
        var enabled = true

        for (insn in input) {
            when {
                insn == Insn.Enable -> enabled = true
                insn == Insn.Disable -> enabled = false
                insn is Insn.Mul && enabled -> sum += insn.a * insn.b
            }
        }

        return sum
    }
}
