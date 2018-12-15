package com.tylerkindy.aoc2018

import com.google.common.io.Resources

private val CLAIM_REGEX = Regex("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$")

fun main() {
    val data = Resources.getResource("in/3.txt").readText()
    val claims = parseClaims(data)
    val map = produceMap(claims)

    println("Overlaps: ${getOverlapCount(map)}")
    println("Unique claim: ${findUniqueClaim(claims, map)}")
}

fun parseClaims(data: String): Map<Int, Claim> {
    return data.split('\n')
        .filter(String::isNotEmpty)
        .map { claimStr ->
            val match = CLAIM_REGEX.matchEntire(claimStr)
                ?: throw IllegalStateException("Bad claim: $claimStr")

            val (id, x, y, width, height) = match.destructured
            val idNum = id.toInt()
            idNum to Claim(
                idNum,
                x.toInt(),
                y.toInt(),
                width.toInt(),
                height.toInt()
            )
        }
        .toMap()
}

fun getOverlapCount(map: ClaimMap): Int {
    return map.filterValues { it.size > 1 }
        .size
}

fun produceMap(claims: Map<Int, Claim>): ClaimMap {
    return claims.values.fold(mutableMapOf()) { map, claim ->
        val rangeX = claim.x until claim.x + claim.width
        val rangeY = claim.y until claim.y + claim.height

        val claimFunc = stakeClaim(claim.id, map)
        produceCoords(rangeX, rangeY).forEach(claimFunc)
        map
    }
}

fun <K> stakeClaim(id: Int, map: MutableMap<K, List<Int>>): (K) -> Unit {
    return { key ->
        map[key] = map.getOrDefault(key, emptyList()) + id
    }
}

fun produceCoords(r1: IntRange, r2: IntRange): Collection<Coord> {
    return r1.flatMap { x ->
        r2.map { y -> Pair(x, y) }
    }
}

fun findUniqueClaim(claims: Map<Int, Claim>, map: ClaimMap): Int {
    map.filterValues { it.size == 1 }
        .values
        .flatten()
        .groupBy { it }
        .forEach {  (id, squares) ->
            val numSquares = squares.size

            if (numSquares == claims[id]?.size) {
                return id
            }
        }

    return -1  // should never reach
}

data class Claim(
    val id: Int,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
) {
    val size: Int = width * height
}

typealias Coord = Pair<Int, Int>
typealias ClaimMap = Map<Coord, List<Int>>
