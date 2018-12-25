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

fun getHighScore(numPlayers: Int, lastMarble: Marble): Score {
    val game = createNewGame(numPlayers)

    for (marble in 1..lastMarble) {
        takeTurn(game, marble)
    }

    return game.scores.max()
}

fun createNewGame(numPlayers: Int): MarbleGame {
    val circleNode = ListNode(0)
    circleNode.next = circleNode
    circleNode.prev = circleNode

    val scoresNode = ListNode.from((0 until numPlayers).map { 0L })

    return MarbleGame(
        circle = circleNode,
        scores = scoresNode
    )
}

fun takeTurn(game: MarbleGame, marble: Marble) {
    if (marble % 23 == 0) {
        game.circle = game.circle.back(6)
        game.scores.element += marble + game.circle.removeBefore()
    } else {
        game.circle = game.circle.forward()
        game.circle.addAfter(marble)
        game.circle = game.circle.forward()
    }

    game.scores = game.scores.forward()
}

data class MarbleGame(
    var circle: ListNode<Marble>,
    var scores: ListNode<Score>
)

class ListNode<T>(
    var element: T,
    var next: ListNode<T>? = null,
    var prev: ListNode<T>? = null
) : Iterable<T>
        where T : Number, T : Comparable<T> {

    companion object {
        fun <T> from(iterable: Iterable<T>): ListNode<T> where T : Number, T : Comparable<T> {
            var node: ListNode<T>? = null
            var first: ListNode<T>? = null

            for (num in iterable) {
                if (node == null) {
                    node = ListNode(num, null, null)
                    first = node
                } else {
                    node.next = ListNode(num, null, node)
                    node = node.next
                }
            }

            node!!.next = first!!
            first.prev = node

            return first
        }
    }

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var curNode = this@ListNode
            var pastFirst = false

            override fun hasNext() = !(pastFirst && curNode === this@ListNode)

            override fun next(): T {
                pastFirst = true
                val num = curNode.element
                curNode = curNode.next!!
                return num
            }

        }
    }

    fun back(num: Int = 1): ListNode<T> {
        return move(num, ListNode<T>::prev)
    }

    fun forward(num: Int = 1): ListNode<T> {
        return move(num, ListNode<T>::next)
    }

    private fun move(num: Int, nextNode: (ListNode<T>) -> ListNode<T>?): ListNode<T> {
        return (0 until num).fold(this) { node, _ ->
            nextNode(node) ?: throw NullPointerException()
        }
    }

    fun addAfter(elem: T) {
        val oldNext = next
        next = ListNode(elem, oldNext, this)
        oldNext?.prev = next
    }

    fun removeBefore(): T {
        val num = prev!!.element
        val newPrev = prev!!.prev
        prev = newPrev
        newPrev!!.next = this
        return num
    }

    fun max(): T {
        var curMax = element

        for (n in this) {
            if (n > curMax) curMax = n
        }

        return curMax
    }

    fun toList(): List<T> {
        val list = mutableListOf<T>()

        for (num in this) {
            list += num
        }

        return list
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListNode<*>

        return this.toList() == other.toList()
    }

    override fun hashCode(): Int {
        return this.toList().hashCode()
    }
}

data class MarbleParams(val numPlayers: Int, val lastMarble: Marble)

typealias Marble = Int
typealias Score = Long
