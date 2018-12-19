package com.tylerkindy.aoc2018

import com.google.common.io.Resources

val STEP_REGEX = Regex("^Step (\\w) must be finished before step (\\w) can begin.$")

fun main() {
    val input = Resources.getResource("in/7.txt").readText()
    val stepData = parseSteps(input)

    println("Ordering: ${findOrder(stepData)}")
    println("Time: ${findCompletionTime(stepData, 5, 60)}")
}

fun parseSteps(input: String): StepData {
    return input.split('\n')
        .asSequence()
        .filter(String::isNotEmpty)
        .map(STEP_REGEX::matchEntire)
        .filterNotNull()
        .fold(
            StepData(emptyList(), emptyMap(), emptyMap())
        ) { (steps, forward, backward), match ->
            val (fromStep, toStep) = match.destructured
            val fromChar = fromStep[0]
            val toChar = toStep[0]

            StepData(
                steps + fromChar + toChar,
                forward.add(fromChar, toChar),
                backward.add(toChar, fromChar)
            )
        }
        .let {
            StepData(it.steps.distinct().sorted(), it.forwardGraph, it.backwardGraph)
        }
}

fun StepGraph.add(fromStep: Step, toStep: Step): StepGraph {
    return this + (fromStep to getOrDefault(fromStep, emptySet()) + toStep)
}

fun findOrder(stepData: StepData): String {
    val (steps, _, backward) = stepData
    val ordering = mutableListOf<Step>()

    while (ordering.size < steps.size) {
        ordering += steps.first {
            it !in ordering && (it !in backward || (backward[it]!! - ordering).isEmpty())
        }
    }

    return ordering.joinToString("")
}

fun findCompletionTime(stepData: StepData, numWorkers: Int, timeOffset: Int): Int {
    val (steps, _, backward) = stepData
    val complete = mutableListOf<Step>()
    val inProgress = mutableMapOf<Char, Int>()
    var freeWorkers = numWorkers
    var time = 0

    while (complete.size < steps.size) {
        inProgress.filterValues { it == 0 }
            .forEach { (step, _) ->
                complete += step
                inProgress -= step
                freeWorkers += 1
            }

        (0 until freeWorkers).forEach { _ ->
            val nextStep = steps.firstOrNull {
                it !in complete
                        && it !in inProgress
                        && (it !in backward || (backward[it]!! - complete).isEmpty())
            }

            if (nextStep != null && freeWorkers > 0) {
                inProgress[nextStep] = getTime(nextStep, timeOffset)
                freeWorkers -= 1
            }
        }

        val advance = inProgress.values.min() ?: break
        time += advance
        inProgress.forEach { (step, timeLeft) ->
            inProgress[step] = timeLeft - advance
        }
    }

    return time
}

fun getTime(step: Char, timeOffset: Int): Int {
    return step - 'A' + 1 + timeOffset
}

data class StepData(
    val steps: List<Step>,
    val forwardGraph: StepGraph,
    val backwardGraph: StepGraph
)

typealias StepGraph = Map<Step, Set<Step>>
typealias Step = Char
