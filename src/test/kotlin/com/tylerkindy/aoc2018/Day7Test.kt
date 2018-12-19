package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day7Test {
    val input = "Step C must be finished before step A can begin.\n" +
            "Step C must be finished before step F can begin.\n" +
            "Step A must be finished before step B can begin.\n" +
            "Step A must be finished before step D can begin.\n" +
            "Step B must be finished before step E can begin.\n" +
            "Step D must be finished before step E can begin.\n" +
            "Step F must be finished before step E can begin.\n"
    lateinit var stepData: StepData

    @BeforeEach
    fun setUp() {
        stepData = parseSteps(input)
    }

    @Test
    fun itParsesSteps() {
        assertThat(stepData).isEqualTo(
            StepData(
                listOf('A', 'B', 'C', 'D', 'E', 'F'),
                mapOf(
                    'C' to setOf('A', 'F'),
                    'A' to setOf('B', 'D'),
                    'B' to setOf('E'),
                    'D' to setOf('E'),
                    'F' to setOf('E')
                ),
                mapOf(
                    'A' to setOf('C'),
                    'B' to setOf('A'),
                    'D' to setOf('A'),
                    'E' to setOf('B', 'D', 'F'),
                    'F' to setOf('C')
                )
            )
        )
    }

    @Test
    fun itAddsSteps() {
        assertThat(emptyMap<Step, Set<Step>>().add('C', 'A'))
            .isEqualTo(mapOf('C' to setOf('A')))

        assertThat(mapOf('C' to setOf('A', 'F'), 'A' to setOf('B')).add('A', 'D'))
            .isEqualTo(mapOf('C' to setOf('A', 'F'), 'A' to setOf('B', 'D')))
    }

    @Test
    fun itFindsOrder() {
        assertThat(findOrder(stepData)).isEqualTo("CABDFE")
    }

    @Test
    fun itFindsCompletionTime() {
        assertThat(findCompletionTime(stepData, 2, 0))
            .isEqualTo(15)
    }

    @Test
    fun itGetsTime() {
        assertThat(getTime('A', 60)).isEqualTo(61)
        assertThat(getTime('Z', 60)).isEqualTo(86)
    }
}
