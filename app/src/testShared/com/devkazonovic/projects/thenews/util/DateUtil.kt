package com.devkazonovic.projects.thenews.util

import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

/**
 * This Singleton that provide some Fixed Java Date&Time object
 **/
object DateUtil {

    fun utc_date2022_01_1_time0_0_0(): Instant {
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

    fun utc_date2022_01_2_time0_0_0(): Instant {
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

    fun utc_date2021_2_1_time0_0_0(): Instant {
        return ZonedDateTime.of(
            2021,
            2,
            1,
            0,
            0,
            0,
            0,
            ZoneOffset.UTC
        ).toInstant()
    }

    fun utc_date2021_1_20_time0_0_0(): Instant {
        return ZonedDateTime.of(
            2021,
            1,
            20,
            0,
            0,
            0,
            0,
            ZoneOffset.UTC
        ).toInstant()

    }

    fun utc_date2021_12_20_time11_0_0(): Instant {
        return ZonedDateTime.of(
            2021,
            12,
            20,
            11,
            0,
            0,
            0,
            ZoneOffset.UTC
        ).toInstant()

    }

    fun utc_date2021_12_20_time10_20_0(): Instant {
        return ZonedDateTime.of(
            2021,
            12,
            20,
            10,
            20,
            0,
            0,
            ZoneOffset.UTC
        ).toInstant()

    }
}