package com.grahamedgecombe.advent2024.day9

import com.grahamedgecombe.advent2024.Puzzle
import kotlin.collections.toMutableList

object Day9 : Puzzle<List<Int?>>(9) {
    override fun parse(input: Sequence<String>): List<Int?> {
        val line = input.single()
        val blocks = mutableListOf<Int?>()
        var id = 0

        for (i in 0 until line.length step 2) {
            val fileBlocks = line[i].digitToInt()
            repeat(fileBlocks) {
                blocks += id
            }

            if ((i + 1) < line.length) {
                val freeBlocks = line[i + 1].digitToInt()
                repeat(freeBlocks) {
                    blocks += null
                }
            }

            id++
        }

        return blocks
    }

    override fun solvePart1(input: List<Int?>): Long {
        val blocks = input.toMutableList()
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
}
