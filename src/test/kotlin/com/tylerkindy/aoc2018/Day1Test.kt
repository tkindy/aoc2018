package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun itParsesFrequencies() {
        val data = "+5\n+3\n-9\n+78549\n+4\n"

        assertThat(parseFrequencies(data)).isEqualTo(listOf(5, 3, -9, 78549, 4))
    }

    @Test
    fun itParsesEmptyString() {
        assertThat(parseFrequencies("")).isEqualTo(emptyList<String>())
    }

    @Test
    fun itFindsTheRepeat() {
        assertThat(findRepeat(listOf(1, -1))).isEqualTo(
            Repeat(0, setOf(0, 1))
        )
        assertThat(findRepeat(listOf(3, 3, 4, -2, -4))).isEqualTo(
            Repeat(10, setOf(0, 3, 6, 10, 8, 4, 7))
        )
        assertThat(findRepeat(listOf(-6, 3, 8, 5, -6))).isEqualTo(
            Repeat(5, setOf(0, -6, -3, 5, 10, 4, -2, 1, 9, 14, 8, 2))
        )
        assertThat(findRepeat(listOf(7, 7, -2, -7, -4))).isEqualTo(
            Repeat(14, setOf(0, 7, 14, 12, 5, 1, 8, 15, 13, 6, 2, 9, 16))
        )
        assertThat(findRepeat(listOf(1, -2, 3, 1))).isEqualTo(
            Repeat(2, setOf(0, 1, -1, 2, 3, 4))
        )
    }
}
