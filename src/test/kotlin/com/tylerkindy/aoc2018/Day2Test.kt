package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun itParsesIds() {
        val data = "qysdtiklcagnpfozlwedmhqbvx\n" +
                "qjsdtiklcagnpfozubejmhrbvq\n" +
                "qysdtiklcagnpfozvvejmhrbex\n" +
                "qdsdziklcagnpfouuwejmhrbvx\n" +
                "qysttikqccgnpfozuwejmhrbvx\n"

        assertThat(parseIds(data)).isEqualTo(
            listOf(
                "qysdtiklcagnpfozlwedmhqbvx",
                "qjsdtiklcagnpfozubejmhrbvq",
                "qysdtiklcagnpfozvvejmhrbex",
                "qdsdziklcagnpfouuwejmhrbvx",
                "qysttikqccgnpfozuwejmhrbvx"
            )
        )
    }

    @Test
    fun itGetsChecksum() {
        val ids = listOf(
            "abcdef", "bababc", "abbcde", "abcccd",
            "aabcdd", "abcdee", "ababab"
        )

        assertThat(getChecksum(ids)).isEqualTo(12)
    }

    @Test
    fun itCountsLetters() {
        assertThat(countLetters("abcdef")).isEqualTo(
            mapOf(
                'a' to 1,
                'b' to 1,
                'c' to 1,
                'd' to 1,
                'e' to 1,
                'f' to 1
            )
        )
        assertThat(countLetters("bababc")).isEqualTo(
            mapOf(
                'b' to 3,
                'a' to 2,
                'c' to 1
            )
        )
        assertThat(countLetters("abbcde")).isEqualTo(
            mapOf(
                'a' to 1,
                'b' to 2,
                'c' to 1,
                'd' to 1,
                'e' to 1
            )
        )
        assertThat(countLetters("abcccd")).isEqualTo(
            mapOf(
                'a' to 1,
                'b' to 1,
                'c' to 3,
                'd' to 1
            )
        )
        assertThat(countLetters("aabcdd")).isEqualTo(
            mapOf(
                'a' to 2,
                'b' to 1,
                'c' to 1,
                'd' to 2
            )
        )
        assertThat(countLetters("ababab")).isEqualTo(
            mapOf(
                'a' to 3,
                'b' to 3
            )
        )
    }

    @Test
    fun itGetsSameLetters() {
        val ids = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
        )

        assertThat(getSameLetters(ids)).isEqualTo("fgij")
    }

    @Test
    fun itRemovesStringCharacters() {
        assertThat("abcdef".removeAt(2)).isEqualTo("abdef")
        assertThat("abcdef".removeAt(0)).isEqualTo("bcdef")
        assertThat("abcdef".removeAt(5)).isEqualTo("abcde")
    }
}
