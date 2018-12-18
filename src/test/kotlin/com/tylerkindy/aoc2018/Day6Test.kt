package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day6Test {

    val input = "1, 1\n" +
            "1, 6\n" +
            "8, 3\n" +
            "3, 4\n" +
            "5, 5\n" +
            "8, 9\n"
    lateinit var mapData: MapData
    lateinit var bounds: Bounds

    @BeforeEach
    fun setUp() {
        mapData = parseMap(input)
        bounds = Bounds(1, 8, 9, 1)
    }

    @Test
    fun itParsesMap() {
        assertThat(mapData).isEqualTo(
            MapData(
                mutableMapOf(
                    Pair(1, 1) to 0, Pair(1, 6) to 1,
                    Pair(8, 3) to 2, Pair(3, 4) to 3,
                    Pair(5, 5) to 4, Pair(8, 9) to 5
                ),
                bounds,
                (0 until 6).associate {
                    it to false
                }.toMutableMap()
            )
        )

        /*
            .........
            .0.......
            .........
            ........2
            ...3.....
            .....4...
            .1.......
            .........
            .........
            ........5
         */
    }

    @Test
    fun itGetsBounds() {
        assertThat(mapData.bounds)
            .isEqualTo(bounds)
    }

    @Test
    fun itGetsLargestFiniteArea() {
        assertThat(largestFiniteArea(mapData)).isEqualTo(17)
    }

    @Test
    fun itFloods() {
        flood(mapData)

        assertThat(mapData.map).containsOnly(
            *mapOf(
                Pair(1, 1) to 0, Pair(2, 1) to 0, Pair(3, 1) to 0, Pair(4, 1) to 0,
                Pair(6, 1) to 2, Pair(7, 1) to 2, Pair(8, 1) to 2, Pair(1, 2) to 0,
                Pair(2, 2) to 0, Pair(3, 2) to 3, Pair(4, 2) to 3, Pair(5, 2) to 4,
                Pair(6, 2) to 2, Pair(7, 2) to 2, Pair(8, 2) to 2, Pair(1, 3) to 0,
                Pair(2, 3) to 3, Pair(3, 3) to 3, Pair(4, 3) to 3, Pair(5, 3) to 4,
                Pair(6, 3) to 2, Pair(7, 3) to 2, Pair(8, 3) to 2, Pair(2, 4) to 3,
                Pair(3, 4) to 3, Pair(4, 4) to 3, Pair(5, 4) to 4, Pair(6, 4) to 4,
                Pair(7, 4) to 2, Pair(8, 4) to 2, Pair(1, 5) to 1, Pair(3, 5) to 3,
                Pair(4, 5) to 4, Pair(5, 5) to 4, Pair(6, 5) to 4, Pair(7, 5) to 4,
                Pair(8, 5) to 2, Pair(1, 6) to 1, Pair(2, 6) to 1, Pair(4, 6) to 4,
                Pair(5, 6) to 4, Pair(6, 6) to 4, Pair(7, 6) to 4, Pair(1, 7) to 1,
                Pair(2, 7) to 1, Pair(3, 7) to 1, Pair(4, 7) to 4, Pair(5, 7) to 4,
                Pair(6, 7) to 4, Pair(7, 7) to 5, Pair(8, 7) to 5, Pair(1, 8) to 1,
                Pair(2, 8) to 1, Pair(3, 8) to 1, Pair(4, 8) to 4, Pair(5, 8) to 4,
                Pair(6, 8) to 5, Pair(7, 8) to 5, Pair(8, 8) to 5, Pair(1, 9) to 1,
                Pair(2, 9) to 1, Pair(4, 9) to 5, Pair(5, 9) to 5, Pair(6, 9) to 5,
                Pair(7, 9) to 5, Pair(8, 9) to 5
            ).entries.toTypedArray()
        )

        /*
            .........
            .0000.222
            .00334222
            .03334222
            ..3334422
            .1.344442
            .11.4444.
            .11144455
            .11144555
            .11.55555
         */
    }

    @Test
    fun itFloodsOnce() {
        assertThat(floodOnce(mapData)).isTrue()

        assertThat(mapData.map).containsOnly(
            *mapOf(
                Pair(1, 1) to 0, Pair(2, 1) to 0, Pair(1, 2) to 0, Pair(8, 2) to 2,
                Pair(3, 3) to 3, Pair(7, 3) to 2, Pair(8, 3) to 2, Pair(2, 4) to 3,
                Pair(3, 4) to 3, Pair(4, 4) to 3, Pair(5, 4) to 4, Pair(8, 4) to 2,
                Pair(1, 5) to 1, Pair(3, 5) to 3, Pair(4, 5) to 4, Pair(5, 5) to 4,
                Pair(6, 5) to 4, Pair(1, 6) to 1, Pair(2, 6) to 1, Pair(5, 6) to 4,
                Pair(1, 7) to 1, Pair(8, 8) to 5, Pair(7, 9) to 5, Pair(8, 9) to 5
            ).entries.toTypedArray()
        )
        assertThat(mapData.infinite).containsOnly(
            *(0 until 6).associate {
                it to when (it) {
                    0, 1, 2, 5 -> true
                    else -> false
                }
            }.entries.toTypedArray()
        )

        /*
            .........
            .00......
            .0......2
            ...3...22
            ..3334..2
            .1.3444..
            .11..4...
            .1.......
            ........5
            .......55
         */

        assertThat(floodOnce(mapData)).isTrue()

        assertThat(mapData.map).containsOnly(
            *mapOf(
                Pair(1, 1) to 0, Pair(2, 1) to 0, Pair(3, 1) to 0, Pair(8, 1) to 2,
                Pair(1, 2) to 0, Pair(2, 2) to 0, Pair(3, 2) to 3, Pair(7, 2) to 2,
                Pair(8, 2) to 2, Pair(1, 3) to 0, Pair(2, 3) to 3, Pair(3, 3) to 3,
                Pair(4, 3) to 3, Pair(5, 3) to 4, Pair(6, 3) to 2, Pair(7, 3) to 2,
                Pair(8, 3) to 2, Pair(2, 4) to 3, Pair(3, 4) to 3, Pair(4, 4) to 3,
                Pair(5, 4) to 4, Pair(6, 4) to 4, Pair(7, 4) to 2, Pair(8, 4) to 2,
                Pair(1, 5) to 1, Pair(3, 5) to 3, Pair(4, 5) to 4, Pair(5, 5) to 4,
                Pair(6, 5) to 4, Pair(7, 5) to 4, Pair(8, 5) to 2, Pair(1, 6) to 1,
                Pair(2, 6) to 1, Pair(4, 6) to 4, Pair(5, 6) to 4, Pair(6, 6) to 4,
                Pair(1, 7) to 1, Pair(2, 7) to 1, Pair(5, 7) to 4, Pair(8, 7) to 5,
                Pair(1, 8) to 1, Pair(7, 8) to 5, Pair(8, 8) to 5, Pair(6, 9) to 5,
                Pair(7, 9) to 5, Pair(8, 9) to 5
            ).entries.toTypedArray()
        )
        assertThat(mapData.infinite).containsOnly(
            *(0 until 6).associate {
                it to when (it) {
                    0, 1, 2, 5 -> true
                    else -> false
                }
            }.entries.toTypedArray()
        )

        /*
            .........
            .000....2
            .003...22
            .03334222
            ..3334422
            .1.344442
            .11.444..
            .11..4..5
            .1.....55
            ......555
         */
    }

    @Test
    fun itGetsNeighbors() {
        assertThat(getNeighbors(Pair(3, 2), bounds))
            .isEqualTo(
                setOf(
                    Pair(2, 2), Pair(3, 3), Pair(4, 2), Pair(3, 1)
                )
            )

        assertThat(getNeighbors(Pair(3, 2), Bounds(2, 7, 6, 0)))
            .isEqualTo(
                setOf(
                    Pair(4, 2), Pair(3, 3), Pair(2, 2)
                )
            )

        assertThat(getNeighbors(Pair(3, 2), Bounds(0, 7, 6, 3)))
            .isEqualTo(
                setOf(
                    Pair(3, 1), Pair(4, 2), Pair(3, 3)
                )
            )
    }

    @Test
    fun itKnowsIsAtEdge() {
        assertThat(isAtEdge(Pair(3, 4), Bounds(0, 7, 7, 0))).isFalse()
        assertThat(isAtEdge(Pair(3, 4), Bounds(4, 7, 7, 0))).isTrue()
    }

    @Test
    fun itGetsCloseRegionSize() {
        assertThat(getCloseRegionSize(mapData.map, mapData.bounds, 32)).isEqualTo(16)
    }

    @Test
    fun itGetsTotalDist() {
        assertThat(getTotalDist(mapData.map, Pair(4, 3))).isEqualTo(30)
    }

    @Test
    fun itCalculatesDistance() {
        assertThat(dist(Pair(3, 4), Pair(7, 12))).isEqualTo(12)
        assertThat(dist(Pair(6, 1), Pair(6, 1))).isEqualTo(0)
    }

    @Test
    fun itGetsAllPoints() {
        assertThat(allPoints(Bounds(2, 4, 5, 2)))
            .isEqualTo(
                setOf(
                    Pair(2, 2), Pair(3, 2), Pair(4, 2),
                    Pair(2, 3), Pair(3, 3), Pair(4, 3),
                    Pair(2, 4), Pair(3, 4), Pair(4, 4),
                    Pair(2, 5), Pair(3, 5), Pair(4, 5)
                )
            )
    }
}
