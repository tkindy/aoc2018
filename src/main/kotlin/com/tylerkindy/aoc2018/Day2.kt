package com.tylerkindy.aoc2018

import com.google.common.io.Resources

fun main() {
    val data = Resources.getResource("in/2.txt").readText()
    val ids = parseIds(data)
    val checksum = getChecksum(ids)

    println("Checksum: $checksum")
}

fun parseIds(data: String): List<String> = data.split('\n')
    .filter(String::isNotEmpty)

fun getChecksum(ids: List<String>): Int {
    return ids.fold(Pair(0, 0)) { (twos, threes), id ->
        val letterCounts = countLetters(id).values

        Pair(
            twos + if (2 in letterCounts) 1 else 0,
            threes + if (3 in letterCounts) 1 else 0
        )
    }
        .let { (twos, threes) -> twos * threes }
}

fun countLetters(id: String): Map<Char, Int> {
    return id.fold(emptyMap()) { charCounts, char ->
        charCounts + (char to charCounts.getOrDefault(char, 0) + 1)
    }
}
