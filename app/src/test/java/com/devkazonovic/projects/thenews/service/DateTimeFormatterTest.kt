package com.devkazonovic.projects.thenews.service

import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.util.DateUtil.utc_date2021_12_20_time10_20_0
import com.devkazonovic.projects.thenews.util.DateUtil.utc_date2021_12_20_time11_0_0
import com.devkazonovic.projects.thenews.util.DateUtil.utc_date2021_1_20_time0_0_0
import com.devkazonovic.projects.thenews.util.DateUtil.utc_date2021_2_1_time0_0_0
import com.devkazonovic.projects.thenews.util.DateUtil.utc_date2022_01_1_time0_0_0
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset

/**Google RSS Date Format: Sun, 19 Dec 2021 11:40:00 GMT*/
class DateTimeUtilTest {

    private lateinit var dateTimeFormatter: DateTimeFormatter

    @Test
    fun test_fromGoogleNewsFormat_toInstant() {
        //Given
        val publishedDate = "Sun, 19 Dec 2021 11:40:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.systemUTC())
        val expectedInstant = Instant.ofEpochMilli(1639914000000L)

        //When
        val actualInstance = dateTimeFormatter.getHowMuchTimePassed(publishedDate)

        //Then
        assertThat(actualInstance).isEqualTo(expectedInstant)
    }

    @Test
    fun test_YearsAgo() {
        //Given
        val publishedDate = "Sun, 19 Dec 2021 00:00:00 GMT"
        val currentDate = utc_date2022_01_1_time0_0_0()
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(currentDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.calcTimePassed(publishedDate)

        //Then
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.YEAR))
        }
    }

    @Test
    fun test_MonthsAgo() {
        //Given
        val publishedDate = "Sun, 19 Jan 2021 00:00:00 GMT"
        val currentDate = utc_date2021_2_1_time0_0_0()
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(currentDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.calcTimePassed(publishedDate)

        //Then
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.MONTH))
        }
    }

    @Test
    fun test_DaysAgo() {
        //Given
        val currentDate = "Sun, 19 Jan 2021 00:00:00 GMT"
        val publishedDate = utc_date2021_1_20_time0_0_0()
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(publishedDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.calcTimePassed(currentDate)

        //Then
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.DAY))
        }
    }

    @Test
    fun test_HoursAgo() {
        //Given
        val currentDate = "Sun, 20 Dec 2021 10:00:00 GMT"
        val publishedDate = utc_date2021_12_20_time11_0_0()
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(publishedDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.calcTimePassed(currentDate)

        //Then
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.HOUR))
        }
    }

    @Test
    fun test_MinutesAgo() {
        //Given
        val publishedDate = utc_date2021_12_20_time10_20_0()
        val currentDate = "Sun, 20 Dec 2021 10:19:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(publishedDate, ZoneOffset.UTC))
        println(publishedDate)

        //When
        val value = dateTimeFormatter.calcTimePassed(currentDate)

        //Then
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.MINUTE))
        }
    }

}