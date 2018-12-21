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
            mutableListOf(0),
            0,
            mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            0
        )
        game1 = MarbleGame(
            mutableListOf(0, 1),
            1,
            game0.scores,
            1
        )
        game2 = MarbleGame(
            mutableListOf(0, 2, 1),
            1,
            game0.scores,
            2
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
    fun itGetsNextMarbleIndex() {
        assertThat(getNextMarbleIndex(game0.circle, game0.curMarble)).isEqualTo(1)
        assertThat(getNextMarbleIndex(game1.circle, game1.curMarble)).isEqualTo(1)
        assertThat(getNextMarbleIndex(game2.circle, game2.curMarble)).isEqualTo(3)
    }
}
