package com.grahamedgecombe.advent2024.day22

import com.grahamedgecombe.advent2024.Puzzle

object Day22 : Puzzle<List<Int>>(22) {
    override fun parse(input: Sequence<String>): List<Int> {
        return input.map(String::toInt).toList()
    }

    override fun solvePart1(input: List<Int>): Long {
        return input.sumOf { secret ->
            next(secret, 2000).toLong()
        }
    }

    private fun next(secret: Int, rounds: Int): Int {
        var secret = secret
        repeat(rounds) {
            secret = (secret xor (secret shl 6)) and 0xFFFFFF
            secret = (secret xor (secret shr 5)) and 0xFFFFFF
            secret = (secret xor (secret shl 11)) and 0xFFFFFF
        }
        return secret
    }
}
