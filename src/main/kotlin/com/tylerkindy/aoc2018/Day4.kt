package com.tylerkindy.aoc2018

import com.google.common.io.Resources
import java.time.Duration
import java.time.LocalDateTime

val EVENT_REGEX =
    Regex("^\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)] (Guard #(\\d+) begins shift|falls asleep|wakes up)$")

fun main() {
    val input = Resources.getResource("in/4.txt").readText()
    val timeline = parseInput(input)

    println("Strategy 1: ${getSleepiestProduct(timeline)}")
    println("Strategy 2: ${strategy2(timeline)}")
}

fun parseInput(input: String): Timeline {
    val guardIds = mutableSetOf<GuardId>()
    val events = input.split('\n')
        .filter(String::isNotEmpty)
        .sorted()
        .fold(emptyList<Event>()) { events, line ->
            val lastGuardId = events.lastOrNull()?.guardId?.also { guardIds += it }
            events + parseEvent(line, lastGuardId)
        }

    return Timeline(guardIds, events)
}

fun parseEvent(line: String, lastGuardId: Int?): Event {
    val match = EVENT_REGEX.matchEntire(line) ?: throw IllegalArgumentException("Bad line: $line")
    val (year, month, day, hour, minute,
            eventInfo, guardId) = match.destructured

    val (guard, eventType) = when (eventInfo) {
        "falls asleep" -> Pair(lastGuardId!!, EventType.SLEEP)
        "wakes up" -> Pair(lastGuardId!!, EventType.WAKE)
        else -> Pair(guardId.toInt(), EventType.BEGIN_SHIFT)
    }

    return Event(
        LocalDateTime.of(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt()),
        guard,
        eventType
    )
}

fun getSleepiestGuard(timeline: Timeline): GuardId {
    return timeline.events
        .foldIndexed(emptyMap<GuardId, Duration>()) { index, sleepMap, event ->
            if (event.eventType != EventType.SLEEP) {
                return@foldIndexed sleepMap
            }

            val prevMinutes = sleepMap.getOrDefault(event.guardId, Duration.ZERO)
            val wakeEvent = timeline.events[index + 1]
            val timeAsleep = Duration.between(event.dateTime, wakeEvent.dateTime)

            sleepMap + (event.guardId to prevMinutes + timeAsleep)
        }
        .maxBy(Map.Entry<GuardId, Duration>::value)!!.key
}

fun getSleepiestProduct(timeline: Timeline): Int {
    val sleepiestGuard = getSleepiestGuard(timeline)
    val (sleepiestMinute, _) = getSleepiestMinute(timeline, sleepiestGuard)
    return sleepiestGuard * sleepiestMinute
}

fun getSleepiestMinute(timeline: Timeline, guardId: GuardId): Pair<Int, Int?> {
    return timeline.events
        .foldIndexed(emptyMap<Int, Int>()) { index, minuteMap, event ->
            if (event.guardId != guardId || event.eventType != EventType.SLEEP) {
                return@foldIndexed minuteMap
            }

            val wakeEvent = timeline.events[index + 1]
            addMinutes(minuteMap, event.dateTime, wakeEvent.dateTime)
        }
        .maxBy(Map.Entry<Int, Int>::value)?.toPair() ?: Pair(guardId, null)
}

fun addMinutes(minuteMap: Map<Int, Int>, sleepTime: LocalDateTime, wakeTime: LocalDateTime): Map<Int, Int> {
    val timeAsleep = Duration.between(sleepTime, wakeTime)

    return (0 until timeAsleep.toMinutes()).fold(minuteMap) { map, min ->
        val curTime = sleepTime.plusMinutes(min)
        val minuteBucket = curTime.minute
        val prevCount = map.getOrDefault(minuteBucket, 0)

        map + (minuteBucket to prevCount + 1)
    }
}

fun strategy2(timeline: Timeline): Int {
    return timeline.guards
        .fold(Triple<GuardId?, Int?, Int?>(null, null, null)) { curChoice, guard ->
            val (_, _, bestCount) = curChoice
            val (sleepiestMinute, count) = getSleepiestMinute(timeline, guard)

            if (bestCount == null || (count != null && count > bestCount)) {
                Triple(guard, sleepiestMinute, count)
            } else {
                curChoice
            }
        }
        .let { it.first!! * it.second!! }
}

data class Timeline(val guards: Set<GuardId>, val events: List<Event>)

data class Event(
    val dateTime: LocalDateTime,
    val guardId: GuardId,
    val eventType: EventType
)

enum class EventType {
    BEGIN_SHIFT, SLEEP, WAKE
}

typealias GuardId = Int
