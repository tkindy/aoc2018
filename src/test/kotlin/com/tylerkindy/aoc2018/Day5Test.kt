package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun itParsesPolymer() {
        assertThat(parsePolymer("")).isEqualTo(emptyList<Char>())
        assertThat(parsePolymer("\n")).isEqualTo(emptyList<Char>())
        assertThat(parsePolymer("aabAAB\n"))
            .isEqualTo(listOf('a', 'a', 'b', 'A', 'A', 'B'))
    }

    @Test
    fun itReactsPolymer() {
        assertThat(reactPolymer("aA".toList()))
            .isEqualTo(emptyList<Char>())
        assertThat(reactPolymer("abBA".toList()))
            .isEqualTo("".toList())
        assertThat(reactPolymer("abAB".toList()))
            .isEqualTo("abAB".toList())
        assertThat(reactPolymer("aabAAB".toList()))
            .isEqualTo("aabAAB".toList())
        assertThat(reactPolymer("dabAcCaCBAcCcaDA".toList()))
            .isEqualTo("dabCBAcaDA".toList())
    }

    @Test
    fun itPerformsPass() {
        assertThat(performPass("aA".toList()))
            .isEqualTo(emptyList<Char>())
        assertThat(performPass("abBA".toList()))
            .isEqualTo("aA".toList())
        assertThat(performPass("abAB".toList()))
            .isEqualTo("abAB".toList())
        assertThat(performPass("aabAAB".toList()))
            .isEqualTo("aabAAB".toList())
        assertThat(performPass("dabAcCaCBAcCcaDA".toList()))
            .isEqualTo("dabAaCBAcaDA".toList())
    }

    @Test
    fun itKnowsWhenUnitsTrigger() {
        assertThat(unitsTrigger('a', 'a')).isFalse()
        assertThat(unitsTrigger('a', 'A')).isTrue()
        assertThat(unitsTrigger('A', 'a')).isTrue()
        assertThat(unitsTrigger('A', 'A')).isFalse()

        assertThat(unitsTrigger('a', 'b')).isFalse()
        assertThat(unitsTrigger('a', 'B')).isFalse()
        assertThat(unitsTrigger('A', 'b')).isFalse()
        assertThat(unitsTrigger('A', 'B')).isFalse()

        assertThat(unitsTrigger('c', 'C')).isTrue()
    }
}
