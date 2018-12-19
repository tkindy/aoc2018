package com.tylerkindy.aoc2018

import com.google.common.io.Resources

fun main() {
    val input = Resources.getResource("in/8.txt").readText()
    val root = parseTree(input)

    println("Metadata sum: ${root.metadataSum}")
    println("Value: ${root.value}")
}

fun parseTree(input: String): Node {
    return input.trim()
        .split(' ')
        .map(String::toInt)
        .let(::parseNode)
        .first
}

fun parseNode(data: List<Int>): Pair<Node, Int> {
    val numChildren = data[0]
    val numMetadata = data[1]

    val (_, children, dataUsed) = (0 until numChildren).fold(
        ChildData(
            data.drop(2).dropLast(numMetadata),
            emptyList(),
            0
        )
    ) { (nextData, parsedChildren, dataUsed), _ ->
        val (nextChild, childDataUsed) = parseNode(nextData)
        ChildData(
            nextData.drop(childDataUsed),
            parsedChildren + nextChild,
            dataUsed + childDataUsed
        )
    }

    val metadataStart = 2 + dataUsed
    val metadata = data.subList(metadataStart, metadataStart + numMetadata)
    return Pair(Node(children, metadata), dataUsed + numMetadata + 2)
}

data class Node(val children: List<Node>, val metadata: List<Int>) {
    val metadataSum: Int
        get() = children.fold(metadata.sum()) { curSum, child ->
            curSum + child.metadataSum
        }

    val value: Int
        get() = if (children.isEmpty()) {
            metadataSum
        } else {
            metadata.fold(0) { curSum, datum ->
                curSum + (children.getOrNull(datum - 1)?.value ?: 0)
            }
        }
}

data class ChildData(
    val nextData: List<Int>,
    val parsedChildren: List<Node>,
    val dataUsed: Int
)
