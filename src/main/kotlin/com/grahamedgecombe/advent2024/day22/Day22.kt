package com.grahamedgecombe.advent2024.day22

import com.grahamedgecombe.advent2024.Puzzle

object Day22 : Puzzle<List<Int>>(22) {
    private const val ROUNDS = 2000

    override fun parse(input: Sequence<String>): List<Int> {
        return input.map(String::toInt).toList()
    }

    override fun solvePart1(input: List<Int>): Long {
        return input.sumOf { secret ->
            next(secret, ROUNDS).toLong()
        }
    }

    private data class ChangeSeq(val a: Int, val b: Int, val c: Int, val d: Int)

    override fun solvePart2(input: List<Int>): Int {
        val bananas = mutableMapOf<ChangeSeq, Int>()

        val changes = ArrayDeque<Int>()
        val seen = mutableSetOf<ChangeSeq>()

        for (secret in input) {
            var secret = secret
            var previousPrice = secret % 10

            changes.clear()
            seen.clear()

            for (i in 0 until ROUNDS) {
                secret = next(secret)
                val price = secret % 10

                changes += price - previousPrice
                previousPrice = price

                if (changes.size > 4) {
                    changes.removeFirst()
                }

                if (changes.size != 4) {
                    continue
                }

                val seq = ChangeSeq(changes[0], changes[1], changes[2], changes[3])
                if (seen.add(seq)) {
                    bananas[seq] = price + bananas.getOrDefault(seq, 0)
                }
            }
        }

        return bananas.values.max()
    }

    private fun next(secret: Int): Int {
        var secret = (secret xor (secret shl 6)) and 0xFFFFFF
        secret = (secret xor (secret shr 5)) and 0xFFFFFF
        return (secret xor (secret shl 11)) and 0xFFFFFF
    }

    private fun next(secret: Int, rounds: Int): Int {
        var secret = secret
        repeat(rounds) {
            secret = next(secret)
        }
        return secret
    }
}
