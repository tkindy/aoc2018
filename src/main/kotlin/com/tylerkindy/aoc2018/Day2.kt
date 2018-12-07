package com.tylerkindy.aoc2018

import com.google.common.io.Resources
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {
    val data = Resources.getResource("in/2.txt").readText()
    val ids = parseIds(data)

    println("Checksum: ${getChecksum(ids)}")
    println("Same letters: ${getSameLetters(ids)}")
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

fun getSameLetters(ids: List<String>): String {
    for (x in ids) {
        for (y in ids) {
            if (x == y) {
                continue
            }

            var diffCount = 0
            var diffIndex = 0

            (0 until x.length).forEach { index ->
                val charX = x[index]
                val charY = y[index]

                if (charX != charY) {
                    diffCount += 1
                    diffIndex = index
                }

                if (diffCount > 1) {
                    return@forEach
                }
            }

            if (diffCount == 1) {
                return x.removeAt(diffIndex)
            }
        }
    }

    throw IllegalStateException("Shouldn't get here!")
}

fun String.removeAt(index: Int): String {
    if (index < 0 || index >= length) {
        throw IllegalArgumentException("index $index out of bounds for string of length $length")
    }

    return take(index) + drop(index + 1)
}
