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
    fun itGetsImprovedPolymerLength() {
        assertThat(getImprovedPolymerLength("dabAcCaCBAcCcaDA".toList()))
            .isEqualTo(4)
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
    }

    @Test
    fun getUnits() {
        assertThat(getUnits(emptyList())).isEqualTo(emptySet<Char>())
        assertThat(getUnits("aA".toList())).isEqualTo(setOf('a'))
        assertThat(getUnits("dabAcCaCBAcCcaDA".toList())).isEqualTo(
            setOf('d', 'a', 'b', 'c')
        )
    }

    @Test
    fun itRemovesUnit() {
        assertThat(removeUnit(emptyList(), 'a'))
            .isEqualTo(emptyList<Char>())
        assertThat(removeUnit("aA".toList(), 'a'))
            .isEqualTo(emptyList<Char>())
        assertThat(removeUnit("abBA".toList(), 'a'))
            .isEqualTo("bB".toList())
        assertThat(removeUnit("abAB".toList(), 'a'))
            .isEqualTo("bB".toList())
        assertThat(removeUnit("aabAAB".toList(), 'a'))
            .isEqualTo("bB".toList())

        assertThat(removeUnit("dabAcCaCBAcCcaDA".toList(), 'a'))
            .isEqualTo("dbcCCBcCcD".toList())
        assertThat(removeUnit("dabAcCaCBAcCcaDA".toList(), 'b'))
            .isEqualTo("daAcCaCAcCcaDA".toList())
        assertThat(removeUnit("dabAcCaCBAcCcaDA".toList(), 'c'))
            .isEqualTo("dabAaBAaDA".toList())
        assertThat(removeUnit("dabAcCaCBAcCcaDA".toList(), 'd'))
            .isEqualTo("abAcCaCBAcCcaA".toList())
    }
}
