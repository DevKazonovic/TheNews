package com.devkazonovic.projects.thenews.androidTestHelp

import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import org.threeten.bp.Clock
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

object DateUtil {

    val dateFormatterFixedOn2022_1_1_0 = DateTimeFormatter(
        Clock.fixed(get2022_01_1(), ZoneOffset.UTC)
    )

    fun get2022_01_1(): Instant {
        return ZonedDateTime.of(
            2022,
            1,
            1,
            0,
            0,
            0,
            0,
            ZoneOffset.UTC
        ).toInstant()
    }

    fun get2022_01_2(): Instant {
        return ZonedDateTime.of(
            2022,
            1,
            2,
            0,
            0,
            0,
            0,
            ZoneOffset.UTC
        ).toInstant()
    }
}