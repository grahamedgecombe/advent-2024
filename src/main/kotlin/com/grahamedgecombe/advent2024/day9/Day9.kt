package com.grahamedgecombe.advent2024.day9

import com.grahamedgecombe.advent2024.Puzzle
import com.grahamedgecombe.advent2024.day9.Day9.Span
import kotlin.collections.toMutableList

object Day9 : Puzzle<List<Span>>(9) {
    data class Span(val id: Int?, val blocks: Int)

    override fun parse(input: Sequence<String>): List<Span> {
        val line = input.single()
        val spans = mutableListOf<Span>()
        var id = 0

        for (i in 0 until line.length step 2) {
            val fileBlocks = line[i].digitToInt()
            spans += Span(id, fileBlocks)

            if ((i + 1) < line.length) {
                val freeBlocks = line[i + 1].digitToInt()
                spans += Span(null, freeBlocks)
            }

            id++
        }

        return spans
    }

    override fun solvePart1(input: List<Span>): Long {
        val blocks = mutableListOf<Int?>()
        for (span in input) {
            repeat(span.blocks) {
                blocks += span.id
            }
        }

        var lastFreeBlock = -1
        for (i in blocks.size - 1 downTo 0) {
            val id = blocks[i] ?: continue

            val j = blocks.freeIndexAfter(lastFreeBlock)
            if (j !in 0 until i) {
                break
            }

            blocks[i] = null
            blocks[j] = id
            lastFreeBlock = j
        }

        var checksum = 0L
        for ((i, id) in blocks.withIndex()) {
            if (id != null) {
                checksum += i.toLong() * id.toLong()
            }
        }
        return checksum
    }

    private fun List<Int?>.freeIndexAfter(start: Int): Int {
        for (i in start + 1 until size) {
            if (this[i] == null) {
                return i
            }
        }

        return -1
    }

    override fun solvePart2(input: List<Span>): Long {
        val spans = input.toMutableList()

        var i = spans.size - 1
        while (i >= 0) {
            val span = spans[i]
            if (span.id == null) {
                i--
                continue
            }

            val j = spans.indexOfFirst { s -> s.id == null && s.blocks >= span.blocks }
            if (j !in 0 until i) {
                i--
                continue
            }

            val freeSpan = spans[j]

            spans[i] = Span(null, span.blocks)
            spans[j] = span

            val remainingBlocks = freeSpan.blocks - span.blocks
            if (remainingBlocks > 0) {
                spans.add(j + 1, Span(null, remainingBlocks))
                continue
            }

            i--
        }

        var checksum = 0L
        var block = 0
        for (span in spans) {
            repeat(span.blocks) {
                if (span.id != null) {
                    checksum += block.toLong() * span.id.toLong()
                }
                block++
            }
        }
        return checksum
    }
}
