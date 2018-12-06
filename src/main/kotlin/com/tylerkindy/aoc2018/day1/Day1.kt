package com.tylerkindy.aoc2018.day1

import com.google.common.io.Resources

fun main() {
    val data = Resources.getResource("in/1.txt").readText()
    val freqs = parseFrequencies(data)
    println(freqs)

    println("One-run sum: ${freqs.sum()}")

    println("Repeat: ${findRepeat(freqs)}")
}

fun parseFrequencies(data: String): List<Int> {
    return data.split('\n')
        .filter(String::isNotEmpty)
        .map(String::toInt)
}

fun findRepeat(freqs: List<Int>): Int {
    var curSum = 0
    var pastSums = setOf(curSum)

    var index = 0

    while (true) {
        println("freq: ${freqs[index]}")
        curSum += freqs[index]
        println(curSum)

        if (curSum in pastSums) {
            return curSum
        }

        pastSums += curSum
        index = (index + 1) % freqs.size
    }
}
