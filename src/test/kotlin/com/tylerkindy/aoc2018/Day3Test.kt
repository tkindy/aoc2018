package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3Test {

    private val claims = listOf(
        Claim(1, 1, 3, 4, 4),
        Claim(2, 3, 1, 4, 4),
        Claim(3, 5, 5, 2, 2)
    )

    @Test
    fun itParsesClaims() {
        val data = "#1 @ 520,746: 4x20\n" +
                "#2 @ 274,680: 19x26\n" +
                "#3 @ 928,402: 16x24\n" +
                "#243 @ 42,901: 500x10\n"

        assertThat(parseClaims(data)).containsOnly(
            Claim(1, 520, 746, 4, 20),
            Claim(2, 274, 680, 19, 26),
            Claim(3, 928, 402, 16, 24),
            Claim(243, 42, 901, 500, 10)
        )
    }

    @Test
    fun itCountsOverlaps() {
        assertThat(getOverlapCount(claims)).isEqualTo(4)
    }

    @Test
    fun itProducesMap() {
        val one = listOf(1)
        val two = listOf(2)
        val three = listOf(3)
        val oneTwo = listOf(1, 2)

        assertThat(produceMap(claims)).isEqualTo(
            mapOf(
                Pair(3, 1) to two, Pair(4, 1) to two, Pair(5, 1) to two,
                Pair(6, 1) to two, Pair(3, 2) to two, Pair(4, 2) to two,
                Pair(5, 2) to two, Pair(6, 2) to two, Pair(1, 3) to one,
                Pair(2, 3) to one, Pair(3, 3) to oneTwo, Pair(4, 3) to oneTwo,
                Pair(5, 3) to two, Pair(6, 3) to two, Pair(1, 4) to one,
                Pair(2, 4) to one, Pair(3, 4) to oneTwo, Pair(4, 4) to oneTwo,
                Pair(5, 4) to two, Pair(6, 4) to two, Pair(1, 5) to one,
                Pair(2, 5) to one, Pair(3, 5) to one, Pair(4, 5) to one,
                Pair(5, 5) to three, Pair(6, 5) to three, Pair(1, 6) to one,
                Pair(2, 6) to one, Pair(3, 6) to one, Pair(4, 6) to one,
                Pair(5, 6) to three, Pair(6, 6) to three
            )
        )
    }

    @Test
    fun itStakesClaims() {
        val map = mutableMapOf(
            "a" to listOf(1, 2, 4),
            "tyler" to listOf(2, 3)
        )
        val claimFunc = stakeClaim(5, map)

        claimFunc("a")

        assertThat(map).isEqualTo(
            mapOf(
                "a" to listOf(1, 2, 4, 5),
                "tyler" to listOf(2, 3)
            )
        )

        claimFunc("new key")

        assertThat(map).isEqualTo(
            mapOf(
                "a" to listOf(1, 2, 4, 5),
                "tyler" to listOf(2, 3),
                "new key" to listOf(5)
            )
        )
    }

    @Test
    fun itProducesCoordinates() {
        assertThat(produceCoords(0 until 3, 2 until 4)).containsOnly(
            Pair(0, 2),
            Pair(0, 3),
            Pair(1, 2),
            Pair(1, 3),
            Pair(2, 2),
            Pair(2, 3)
        )
    }

    @Test
    fun itFindsUniqueClaim() {
        assertThat(findUniqueClaim(claims)).isEqualTo(3)
    }
}
