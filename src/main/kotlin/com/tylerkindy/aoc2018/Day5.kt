package com.tylerkindy.aoc2018

import com.google.common.io.Resources

fun main() {
    val input = Resources.getResource("in/5.txt").readText()
    val polymer = parsePolymer(input)

    println("Units remaining: ${reactPolymer(polymer).size}")
}

fun parsePolymer(input: String): Polymer {
    return input.toList().filter(Char::isLetter)
}

fun reactPolymer(polymer: Polymer): Polymer {
    var prev = polymer
    var next = performPass(prev)

    while (next != prev) {
        prev = next
        next = performPass(prev)
    }

    return next
}

fun performPass(polymer: Polymer): Polymer {
    return polymer.asSequence().windowed(2, partialWindows = true)
        .fold(Pair(emptyList<Char>(), false)) { (newPolymer, leftConsumed), window ->
            when {
                leftConsumed -> Pair(newPolymer, false)
                window.size < 2 -> Pair(newPolymer + window[0], false)
                unitsTrigger(window[0], window[1]) -> Pair(newPolymer, true)
                else -> Pair(newPolymer + window[0], false)
            }
        }.first
}

fun unitsTrigger(left: Char, right: Char): Boolean {
    return left.isUpperCase() && left.toLowerCase() == right
            || left.isLowerCase() && left.toUpperCase() == right
}

typealias Polymer = List<Char>
