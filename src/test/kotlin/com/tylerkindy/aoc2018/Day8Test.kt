package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day8Test {

    val input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2\n"
    val d = Node(emptyList(), listOf(99))
    val c = Node(listOf(d), listOf(2))
    val b = Node(emptyList(), listOf(10, 11, 12))
    val a = Node(listOf(b, c), listOf(1, 1, 2))

    @Test
    fun itParsesTree() {
        assertThat(parseTree(input)).isEqualTo(a)
    }

    @Test
    fun itParsesNode() {
        assertThat(parseNode(listOf(0, 1, 99, 2))).isEqualTo(Pair(d, 3))
        assertThat(parseNode(listOf(1, 1, 0, 1, 99, 2))).isEqualTo(Pair(c, 6))
        assertThat(
            parseNode(listOf(0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2))
        ).isEqualTo(Pair(b, 5))
        assertThat(
            parseNode(
                listOf(2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2)
            )
        ).isEqualTo(Pair(a, 16))
    }

    @Test
    fun itSumsMetadata() {
        assertThat(d.metadataSum).isEqualTo(99)
        assertThat(c.metadataSum).isEqualTo(101)
        assertThat(b.metadataSum).isEqualTo(33)
        assertThat(a.metadataSum).isEqualTo(138)
    }

    @Test
    fun itCalculatesValue() {
        assertThat(d.value).isEqualTo(99)
        assertThat(c.value).isEqualTo(0)
        assertThat(b.value).isEqualTo(33)
        assertThat(a.value).isEqualTo(66)
    }
}
