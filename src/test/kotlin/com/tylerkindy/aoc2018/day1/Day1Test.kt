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

    @Test
    fun itFindsTheRepeat() {
        assertThat(findRepeat(listOf(1, -1))).isEqualTo(0)
        assertThat(findRepeat(listOf(3, 3, 4, -2, -4))).isEqualTo(10)
        assertThat(findRepeat(listOf(-6, 3, 8, 5, -6))).isEqualTo(5)
        assertThat(findRepeat(listOf(7, 7, -2, -7, -4))).isEqualTo(14)
        assertThat(findRepeat(listOf(1, -2, 3, 1))).isEqualTo(2)
    }
}
