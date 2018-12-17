package com.tylerkindy.aoc2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class Day4Test {

    private val input = "[1518-11-01 00:05] falls asleep\n" +
            "[1518-11-01 00:00] Guard #10 begins shift\n" +
            "[1518-11-01 23:58] Guard #99 begins shift\n" +
            "[1518-11-02 00:40] falls asleep\n" +
            "[1518-11-02 00:50] wakes up\n" +
            "[1518-11-03 00:24] falls asleep\n" +
            "[1518-11-03 00:05] Guard #10 begins shift\n" +
            "[1518-11-03 00:29] wakes up\n" +
            "[1518-11-01 00:30] falls asleep\n" +
            "[1518-11-01 00:55] wakes up\n" +
            "[1518-11-01 00:25] wakes up\n"

    private lateinit var timeline: Timeline

    @BeforeEach
    fun setUp() {
        timeline = parseInput(input)
    }

    @Test
    fun itParsesInput() {
        assertThat(timeline).isEqualTo(
            Timeline(
                setOf(10, 99),
                listOf(
                    Event(
                        LocalDateTime.of(1518, 11, 1, 0, 0),
                        10,
                        EventType.BEGIN_SHIFT
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 1, 0, 5),
                        10,
                        EventType.SLEEP
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 1, 0, 25),
                        10,
                        EventType.WAKE
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 1, 0, 30),
                        10,
                        EventType.SLEEP
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 1, 0, 55),
                        10,
                        EventType.WAKE
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 1, 23, 58),
                        99,
                        EventType.BEGIN_SHIFT
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 2, 0, 40),
                        99,
                        EventType.SLEEP
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 2, 0, 50),
                        99,
                        EventType.WAKE
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 3, 0, 5),
                        10,
                        EventType.BEGIN_SHIFT
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 3, 0, 24),
                        10,
                        EventType.SLEEP
                    ),
                    Event(
                        LocalDateTime.of(1518, 11, 3, 0, 29),
                        10,
                        EventType.WAKE
                    )
                )
            )
        )
    }

    @Test
    fun itParsesStartShiftEventWithoutPreviousGuard() {
        val line = "[1518-11-01 23:58] Guard #99 begins shift"

        assertThat(parseEvent(line, null)).isEqualTo(
            Event(
                LocalDateTime.of(1518, 11, 1, 23, 58),
                99,
                EventType.BEGIN_SHIFT
            )
        )
    }

    @Test
    fun itParsesStartShiftEventWithPreviousGuard() {
        val line = "[1518-11-03 00:05] Guard #10 begins shift"

        assertThat(parseEvent(line, 14)).isEqualTo(
            Event(
                LocalDateTime.of(1518, 11, 3, 0, 5),
                10,
                EventType.BEGIN_SHIFT
            )
        )
    }

    @Test
    fun itParsesWakeEvent() {
        val line = "[1518-11-02 00:50] wakes up"

        assertThat(parseEvent(line, 13)).isEqualTo(
            Event(
                LocalDateTime.of(1518, 11, 2, 0, 50),
                13,
                EventType.WAKE
            )
        )
    }

    @Test
    fun itParsesSleepEvent() {
        val line = "[1518-11-05 00:45] falls asleep"

        assertThat(parseEvent(line, 47)).isEqualTo(
            Event(
                LocalDateTime.of(1518, 11, 5, 0, 45),
                47,
                EventType.SLEEP
            )
        )
    }

    @Test
    fun itGetsSleepiestProduct() {
        assertThat(getSleepiestProduct(timeline)).isEqualTo(240)
    }

    @Test
    fun itGetsSleepiestGuard() {
        assertThat(getSleepiestGuard(timeline)).isEqualTo(10)
    }

    @Test
    fun itGetsSleepiestMinute() {
        assertThat(getSleepiestMinute(timeline, 10)).isEqualTo(24)
    }

    @Test
    fun itAddsMinutes() {
        val sleepTime = LocalDateTime.of(1518, 11, 5, 0, 45)
        val wakeTime = LocalDateTime.of(1518, 11, 5, 0, 51)
        assertThat(addMinutes(emptyMap(), sleepTime, wakeTime))
            .isEqualTo(
                mapOf(
                    45 to 1,
                    46 to 1,
                    47 to 1,
                    48 to 1,
                    49 to 1,
                    50 to 1
                )
            )
    }

    @Test
    fun itAddsMinutesOnTopOfOldOnes() {
        val sleepTime = LocalDateTime.of(1518, 11, 5, 0, 45)
        val wakeTime = LocalDateTime.of(1518, 11, 5, 0, 51)
        val minuteMap = mapOf(
            43 to 4, 44 to 1, 45 to 3, 46 to 2
        )
        assertThat(addMinutes(minuteMap, sleepTime, wakeTime))
            .isEqualTo(
                mapOf(
                    43 to 4,
                    44 to 1,
                    45 to 4,
                    46 to 3,
                    47 to 1,
                    48 to 1,
                    49 to 1,
                    50 to 1
                )
            )
    }
}
