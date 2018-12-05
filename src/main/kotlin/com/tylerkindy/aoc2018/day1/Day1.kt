package com.tylerkindy.aoc2018.day1

import com.google.common.io.Resources

fun main() {
    val data = Resources.getResource("in/1.txt").readText()
    val sum = parseFrequencies(data).sum()

    println("One-run sum: $sum")
}

fun parseFrequencies(data: String): List<Int> {
    return data.split('\n')
        .filter(String::isNotEmpty)
        .map(String::toInt)
}
