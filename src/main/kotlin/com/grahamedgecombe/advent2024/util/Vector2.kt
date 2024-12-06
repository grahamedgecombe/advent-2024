package com.grahamedgecombe.advent2024.util

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(v: Vector2): Vector2 {
        return Vector2(x + v.x, y + v.y)
    }
}
