package com.tylerkindy.aoc2018

import com.google.common.io.Resources

private const val NUMBER = "\\s*(-?\\d+)"
private val POINT_REGEX = Regex("^position=<$NUMBER,$NUMBER> velocity=<$NUMBER,$NUMBER>$")

fun main() {
    val input = Resources.getResource("in/10.txt").readText()
    var points = parsePoints(input)

    while (true) {
        printPoints(points)
        println()
        points = movePoints(points)
    }
}

fun parsePoints(input: String): List<Point> {
    return input.split('\n').asSequence()
        .filter(String::isNotEmpty)
        .map(POINT_REGEX::matchEntire)
        .filterNotNull()
        .map(MatchResult::destructured)
        .map { (x, y, dx, dy) ->
            Point(x.toInt(), y.toInt(), dx.toInt(), dy.toInt())
        }
        .toList()
}

fun movePoints(points: List<Point>): List<Point> {
    return points
        .map { (x, y, dx, dy) ->
            Point(x + dx, y + dy, dx, dy)
        }
}

fun printPoints(points: List<Point>) {
    val ordered = points.groupBy(Point::y)
        .mapValues { it.value.sortedBy(Point::x) }

    val left = points.minBy(Point::x)!!.x
    val top = points.minBy(Point::y)!!.y
    val right = points.maxBy(Point::x)!!.x
    val bottom = points.maxBy(Point::y)!!.y

    if (right - left > 500 || bottom - top > 500) {
        return
    }

    for (y in top..bottom) {
        val row = ordered[y]
        var index = 0

        for (x in left..right) {
            if (row != null && row.getOrNull(index)?.x == x) {
                print('#')
                index += 1
            } else {
                print('.')
            }
        }

        println()
    }
}

data class Point(
    val x: Int,
    val y: Int,
    val dx: Int,
    val dy: Int
)
