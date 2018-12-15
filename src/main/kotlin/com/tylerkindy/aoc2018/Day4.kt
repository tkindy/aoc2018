package com.tylerkindy.aoc2018

import com.google.common.io.Resources
import java.time.LocalDateTime

val EVENT_REGEX =
    Regex("^\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)] (Guard #(\\d+) begins shift|falls asleep|wakes up)$")

fun main() {
    val input = Resources.getResource("in/4.txt").readText()
    val timeline = parseInput(input)
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
