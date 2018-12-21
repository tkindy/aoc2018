package com.tylerkindy.aoc2018

import com.google.common.io.Resources

val MARBLE_REGEX = Regex("^(\\d+) players; last marble is worth (\\d+) points$")

fun main() {
    val input = Resources.getResource("in/9.txt").readText()
    val (numPlayers, lastMarble) = parseMarbleParams(input)

    println("High score: ${getHighScore(numPlayers, lastMarble)}")
    println("Higher score: ${getHighScore(numPlayers, lastMarble * 100)}")
}

fun parseMarbleParams(input: String): MarbleParams {
    val match = MARBLE_REGEX.matchEntire(input.trim())
        ?: throw IllegalArgumentException("Input doesn't match: $input")

    val (numPlayers, lastMarble) = match.destructured
    return MarbleParams(numPlayers.toInt(), lastMarble.toInt())
}

fun getHighScore(numPlayers: Int, lastMarble: Marble): Int {
    val onePercent = lastMarble / 100
    val game = createNewGame(numPlayers)

    for (marble in 1..lastMarble) {
        if (onePercent > 0 && marble % onePercent == 0) {
            println(marble / onePercent)
        }

        takeTurn(game, marble)
    }

    return game.scores.max()!!
}

fun createNewGame(numPlayers: Int): MarbleGame {
    return MarbleGame(
        circle = mutableListOf(0),
        curMarble = 0,
        scores = (0 until numPlayers).map { 0 }.toMutableList(),
        curPlayer = 0
    )
}

fun takeTurn(game: MarbleGame, marble: Marble) {
    if (marble % 23 != 0) {
        game.curMarble = getNextMarbleIndex(game.circle, game.curMarble)
        game.circle.add(game.curMarble, marble)
    } else {
        game.curMarble = (game.curMarble - 7).let {
            if (it < 0) game.circle.size + it else it
        }
        val points = marble + game.circle.removeAt(game.curMarble)
        game.scores[game.curPlayer] = game.scores[game.curPlayer] + points
    }

    game.curPlayer = (game.curPlayer + 1) % game.scores.size
}

fun getNextMarbleIndex(circle: List<Marble>, curMarble: Int): Int {
    return ((curMarble + 1) % circle.size) + 1
}

data class MarbleGame(
    val circle: MutableList<Marble>,
    var curMarble: Int,
    val scores: MutableList<Int>,
    var curPlayer: Int
)

data class MarbleParams(val numPlayers: Int, val lastMarble: Marble)

typealias Marble = Int
