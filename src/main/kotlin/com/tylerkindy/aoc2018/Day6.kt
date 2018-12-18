package com.tylerkindy.aoc2018

import com.google.common.io.Resources
import kotlin.math.abs

val COORD_REGEX = Regex("^(\\d+), (\\d+)$")

fun main() {
    val input = Resources.getResource("in/6.txt").readText()
    val mapData = parseMap(input)
    val originalMap = mutableMapOf<Coord, ID>()
    originalMap += mapData.map

    println("Largest finite area: ${largestFiniteArea(mapData)}")
    println("Close region size: ${getCloseRegionSize(originalMap, mapData.bounds, 10_000)}")
}

fun parseMap(input: String): MapData {
    return input.split('\n')
        .asSequence()
        .filter(String::isNotEmpty)
        .map(COORD_REGEX::matchEntire)
        .filterNotNull()
        .map {
            val (x, y) = it.destructured
            Pair(x.toInt(), y.toInt())
        }
        .foldIndexed(
            Pair(mutableMapOf<Coord, ID>(), mutableMapOf<ID, Boolean>())
        ) { index, pair, coord ->
            pair.first[coord] = index
            pair.second[index] = false
            pair
        }
        .let { (map, infinite) ->
            MapData(map, getBounds(map), infinite)
        }
}

fun getBounds(map: CoordMap): Bounds {
    val xs = map.keys.map(Coord::first)
    val ys = map.keys.map(Coord::second)

    return Bounds(ys.min()!!, xs.max()!!, ys.max()!!, xs.min()!!)
}

fun largestFiniteArea(mapData: MapData): Int {
    flood(mapData)

    return mapData.map.entries.fold(mutableMapOf<ID, Int>()) { map, (_, id) ->
        if (mapData.infinite[id] == true) {
            map
        } else {
            map[id] = map.getOrDefault(id, 0) + 1
            map
        }
    }
        .values.max()!!
}

fun flood(mapData: MapData) {
    while (floodOnce(mapData)) continue
}

fun floodOnce(mapData: MapData): Boolean {
    val newClaims: CoordMap = mutableMapOf()
    val duplicateClaims: MutableSet<Coord> = mutableSetOf()

    mapData.map.forEach { coord, id ->
        getNeighbors(coord, mapData.bounds).forEach { neighbor ->
            when {
                neighbor in newClaims && newClaims[neighbor] != id -> {
                    duplicateClaims += neighbor
                }
                neighbor !in mapData.map -> {
                    newClaims += neighbor to id
                }
            }
        }
    }

    newClaims -= duplicateClaims
    mapData.map += newClaims

    newClaims.forEach { coord, id ->
        if (isAtEdge(coord, mapData.bounds))
            mapData.infinite[id] = true
    }

    return newClaims.isNotEmpty()
}

fun getNeighbors(coord: Coord, bounds: Bounds): Set<Coord> {
    val (x, y) = coord
    val (t, r, b, l) = bounds

    val neighbors = mutableSetOf<Coord>()

    if (y > t) neighbors += Pair(x, y - 1)
    if (x < r) neighbors += Pair(x + 1, y)
    if (y < b) neighbors += Pair(x, y + 1)
    if (x > l) neighbors += Pair(x - 1, y)

    return neighbors
}

fun isAtEdge(coord: Coord, bounds: Bounds): Boolean {
    val (x, y) = coord
    val (t, r, b, l) = bounds

    return x == r || x == l || y == t || y == b
}

fun getCloseRegionSize(map: CoordMap, bounds: Bounds, maxDist: Int): Int {
    return allPoints(bounds).fold(0) { size, point ->
        if (getTotalDist(map, point) < maxDist) {
            size + 1
        } else {
            size
        }
    }
}

fun getTotalDist(map: CoordMap, coord: Coord): Int {
    return map.keys.fold(0) { distance, point ->
        distance + dist(coord, point)
    }
}

fun dist(c1: Coord, c2: Coord): Int {
    return abs(c1.first - c2.first) + abs(c1.second - c2.second)
}

fun allPoints(bounds: Bounds): Set<Coord> {
    return (bounds.l..bounds.r).flatMap { x ->
        (bounds.t..bounds.b).map { y ->
            Pair(x, y)
        }
    }.toSet()
}

data class MapData(
    val map: CoordMap,
    val bounds: Bounds,
    val infinite: MutableMap<ID, Boolean>
)

data class Bounds(val t: Int, val r: Int, val b: Int, val l: Int)

typealias CoordMap = MutableMap<Coord, ID>
typealias ID = Int
