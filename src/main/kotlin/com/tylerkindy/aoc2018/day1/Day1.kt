package com.tylerkindy.aoc2018.day1

import com.google.common.io.Resources

fun main() {
    val data = Resources.getResource("in/1.txt").readText()
    val freqs = parseFrequencies(data)

    println("One-run sum: ${freqs.sum()}")
    println("Repeat: ${findRepeat(freqs).sum}")
}

fun parseFrequencies(data: String): List<Freq> {
    return data.split('\n')
        .filter(String::isNotEmpty)
        .map(String::toInt)
}

fun findRepeat(freqs: List<Freq>): Repeat {
    var curSum: Freq = 0
    var index = 0

    val sumSets = (0 until NUM_SETS).map {
        mutableSetOf<Freq>()
    }

    while (true) {
        val setIndex = sumIndex(curSum)
        if (curSum in sumSets[setIndex]) {
            return Repeat(curSum, sumSets.fold(emptySet()) { allSums, curSet -> allSums + curSet })
        }

        sumSets[setIndex] += curSum
        curSum += freqs[index]
        index = (index + 1) % freqs.size
    }
}

fun sumIndex(sum: Freq): Int {
    return (sum + MAX_VAL) / ((2 * MAX_VAL) / NUM_SETS)
}

private const val NUM_SETS = 100
private const val MAX_VAL = 100_000

// For testing
data class Repeat(val sum: Freq, val pastSums: Set<Freq>)

typealias Freq = Int
