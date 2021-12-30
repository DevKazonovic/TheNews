package com.devkazonovic.projects.thenews.service

import com.devkazonovic.projects.thenews.common.util.MonthAbbreviation
import com.devkazonovic.projects.thenews.domain.model.Ago
import org.threeten.bp.*
import javax.inject.Inject

class DateTimeFormatter @Inject constructor(
    private val clock: Clock
) {
    //Format : Thu, 30 Dec 2021 04:08:02 GMT

    fun getHowMuchTimePassed(dateGoogleRssFormat: String): Instant {
        val index = dateGoogleRssFormat.indexOf(",")
        val date = dateGoogleRssFormat.substring(index + 2, 17)
        val time = dateGoogleRssFormat.substring(17)

        val yearDayNumber = date.subSequence(0, 2).toString().toInt()
        val yearMonth = date.substring(3, 7).uppercase().trim()
        val year = date.substring(7, date.length - 1).toInt()

        val localDate = LocalDate.of(
            year,
            MonthAbbreviation.values().indexOf(MonthAbbreviation.valueOf(yearMonth)) + 1,
            yearDayNumber
        )

        val localTime = LocalTime.of(
            time.substringBefore(":").toInt(),
            time.substring(3).substringBefore(":").toInt(),
            time.substring(6, 8).toInt()
        )

        return ZonedDateTime.of(
            LocalDateTime.of(localDate, localTime),
            ZoneOffset.UTC
        ).toInstant()
    }

    fun howMuchAgo(dateGoogleRssFormat: String) : Pair<Int,Ago>{
        val publishedDateInstant = getHowMuchTimePassed(dateGoogleRssFormat)
        val currentDateInstant = Instant.now(clock)

        val publishedOffsetDate = publishedDateInstant.atZone(ZoneOffset.UTC)
        val publishedLocalDate = publishedOffsetDate.toLocalDate()
        val publishedLocalTime = publishedOffsetDate.toLocalTime()

        val currentOffsetDate = currentDateInstant.atZone(clock.zone)
        val currentLocalDate = currentOffsetDate.toLocalDate()
        val currentLocalTime = currentOffsetDate.toLocalTime()

        val yearDiff = currentLocalDate.year - publishedLocalDate.year
        val monthDiff = currentLocalDate.monthValue - publishedLocalDate.monthValue
        val dayDiff = currentLocalDate.dayOfMonth - publishedLocalDate.dayOfMonth
        val hourDiff = currentLocalTime.hour - publishedLocalTime.hour
        val minuteDiff = currentLocalTime.minute - publishedLocalTime.minute

        return when {
            yearDiff>0 -> Pair(yearDiff,Ago.YEAR)
            monthDiff>0 -> Pair(monthDiff,Ago.MONTH)
            dayDiff>0 -> Pair(dayDiff,Ago.DAY)
            hourDiff>0 -> Pair(hourDiff,Ago.HOUR)
            minuteDiff>0 -> Pair(minuteDiff,Ago.MINUTE)
            else -> Pair(0,Ago.NON)
        }
    }
}