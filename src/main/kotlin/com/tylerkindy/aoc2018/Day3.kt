package com.tylerkindy.aoc2018

import com.google.common.io.Resources

private val CLAIM_REGEX = Regex("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$")

fun main() {
    val data = Resources.getResource("in/3.txt").readText()
    val claims = parseClaims(data)

    println("Overlaps: ${getOverlapCount(claims)}")
}

fun parseClaims(data: String): Collection<Claim> {
    return data.split('\n')
        .filter(String::isNotEmpty)
        .map { claimStr ->
            val match = CLAIM_REGEX.matchEntire(claimStr)
                ?: throw IllegalStateException("Bad claim: $claimStr")

            val (id, x, y, width, height) = match.destructured
            Claim(
                id.toInt(),
                x.toInt(),
                y.toInt(),
                width.toInt(),
                height.toInt()
            )
        }
}

fun getOverlapCount(claims: Collection<Claim>): Int {
    return produceMap(claims)
        .filterValues { it.size > 1 }
        .size
}

fun produceMap(claims: Collection<Claim>): Map<Coord, List<Int>> {
    return claims.fold(mutableMapOf()) { map, claim ->
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
    return r1.map { x ->
        r2.map { y -> Pair(x, y) }
    }
        .flatten()
}

fun findUniqueClaim(claims: Collection<Claim>): Int {
    TODO()
}

data class Claim(
    val id: Int,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

typealias Coord = Pair<Int, Int>
