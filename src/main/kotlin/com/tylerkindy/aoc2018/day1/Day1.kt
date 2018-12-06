package com.tylerkindy.aoc2018.day1

import com.google.common.io.Resources

fun main() {
    val data = Resources.getResource("in/1.txt").readText()
    val freqs = parseFrequencies(data)

    println("One-run sum: ${freqs.sum()}")
    val repeat = findRepeat(freqs)
    println("Repeat: ${repeat.sum} (after ${repeat.pastSums.size} sums)")
}

fun parseFrequencies(data: String): List<Int> {
    return data.split('\n')
        .filter(String::isNotEmpty)
        .map(String::toInt)
}

fun findRepeat(freqs: List<Int>): Repeat {
    var curSum = 0
    var index = 0
    val pastSums = mutableSetOf<Int>()

    while (true) {
        if (curSum in pastSums) {
            return Repeat(curSum, pastSums)
        }

        pastSums += curSum
        curSum += freqs[index]
        index = (index + 1) % freqs.size
    }
}

// For testing
data class Repeat(val sum: Int, val pastSums: Set<Int>)
