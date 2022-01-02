package com.devkazonovic.projects.thenews.testHelp

import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

object DateUtil {

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