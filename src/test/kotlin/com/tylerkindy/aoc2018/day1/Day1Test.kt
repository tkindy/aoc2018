package com.tylerkindy.aoc2018.day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun itParsesFrequencies() {
        val data = "5\n3\n-9\n4\n"

        assertThat(parseFrequencies(data)).isEqualTo(listOf(5, 3, -9, 4))
    }

    @Test
    fun itParsesEmptyString() {
        assertThat(parseFrequencies("")).isEqualTo(emptyList<String>())
    }
}
