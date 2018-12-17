package com.tylerkindy.aoc2018

import com.google.common.io.Resources
import kotlin.math.min

fun main() {
    val input = Resources.getResource("in/5.txt").readText()
    val polymer = parsePolymer(input)

    println("Units remaining: ${reactPolymer(polymer).size}")
    println("Improved polymer length: ${getImprovedPolymerLength(polymer)}")
}

fun parsePolymer(input: String): Polymer {
    return input.toList().filter(Char::isLetter)
}

fun getImprovedPolymerLength(polymer: Polymer): Int {
    return getUnits(polymer).fold(polymer.size) { shortestLength, unit ->
        val candidate = removeUnit(polymer, unit)
        min(shortestLength, reactPolymer(candidate).size)
    }
}

fun getUnits(polymer: Polymer): Set<Char> {
    return polymer.fold(emptySet()) { unitSet, unit ->
        unitSet + unit.toLowerCase()
    }
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
    val removedIndices = mutableSetOf<Int>()

    polymer.asSequence()
        .windowed(2, partialWindows = true)
        .forEachIndexed { index, window ->
            when {
                index in removedIndices -> return@forEachIndexed
                window.size < 2 -> return@forEachIndexed
                unitsTrigger(window[0], window[1]) -> {
                    removedIndices += index
                    removedIndices += index + 1
                }
            }
        }

    return polymer.filterIndexed { index, _ ->
        index !in removedIndices
    }
}

fun unitsTrigger(left: Char, right: Char): Boolean {
    return left.isUpperCase() && left.toLowerCase() == right
            || left.isLowerCase() && left.toUpperCase() == right
}

fun removeUnit(polymer: Polymer, unit: Char): Polymer {
    return polymer.filterNot { it.equals(unit, ignoreCase = true) }
}

typealias Polymer = List<Char>
