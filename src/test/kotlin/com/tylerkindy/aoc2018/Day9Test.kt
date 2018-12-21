package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day9Test {

    lateinit var game0: MarbleGame
    lateinit var game1: MarbleGame
    lateinit var game2: MarbleGame

    @BeforeEach
    fun setUp() {
        game0 = MarbleGame(
            ListNode.from(listOf(0)),
            ListNode.from(listOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0))
        )
        game1 = MarbleGame(
            ListNode.from(listOf(1, 0)),
            game0.scores
        )
        game2 = MarbleGame(
            ListNode.from(listOf(2, 1, 0)),
            game1.scores
        )
    }

    @Test
    fun itParsesMarbleParams() {
        assertThat(parseMarbleParams("10 players; last marble is worth 1618 points\n"))
            .isEqualTo(MarbleParams(10, 1618))
    }

    @Test
    fun itGetsHighScore() {
        assertThat(getHighScore(9, 25)).isEqualTo(32)
        assertThat(getHighScore(10, 1618)).isEqualTo(8317)
        assertThat(getHighScore(13, 7999)).isEqualTo(146_373)
        assertThat(getHighScore(17, 1104)).isEqualTo(2764)
        assertThat(getHighScore(21, 6111)).isEqualTo(54_718)
        assertThat(getHighScore(30, 5807)).isEqualTo(37_305)
    }

    @Test
    fun itCreatesNewGame() {
        assertThat(createNewGame(9)).isEqualTo(game0)
    }

    @Test
    fun itTakesTurn() {
        takeTurn(game0, 1)
        assertThat(game0).isEqualTo(game1)

        takeTurn(game0, 2)
        assertThat(game0).isEqualTo(game2)
    }

    @Test
    fun itCreatesListNodes() {
        assertThat(ListNode.from(listOf(0, 1, 2)).toList()).isEqualTo(listOf(0, 1, 2))
    }
}
