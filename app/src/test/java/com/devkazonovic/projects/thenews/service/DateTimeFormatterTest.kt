package com.devkazonovic.projects.thenews.service

import com.devkazonovic.projects.thenews.domain.model.Ago
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.threeten.bp.*

class DateTimeUtilTest {

    //Google RSS Date Format: Sun, 19 Dec 2021 11:40:00 GMT

    private lateinit var dateTimeFormatter: DateTimeFormatter

    @Test
    fun getHowMuchTimePassed() {
        dateTimeFormatter = DateTimeFormatter()
        val actualInstance =
            dateTimeFormatter.getHowMuchTimePassed("Sun, 19 Dec 2021 11:40:00 GMT")
        val expected = Instant.ofEpochMilli(1639914000000L)
        assertThat(actualInstance).isEqualTo(expected)
    }

    @Test
    fun test_YearsAgo(){
        //Given
        val newDate =
            ZonedDateTime.of(2022,1,1,0,0,0,0,ZoneOffset.UTC).toInstant()
        val oldDate = "Sun, 19 Dec 2021 00:00:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(newDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.howMuchAgo(oldDate)

        //Assert
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.YEAR))
        }
    }

    @Test
    fun test_MonthsAgo(){
        //Given
        val newDate =
            ZonedDateTime.of(2021,2,1,0,0,0,0,ZoneOffset.UTC).toInstant()
        val oldDate = "Sun, 19 Jan 2021 00:00:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(newDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.howMuchAgo(oldDate)

        //Assert
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.MONTH))
        }
    }

    @Test
    fun test_DaysAgo(){
        //Given
        val newDate =
            ZonedDateTime.of(2021,1,20,0,0,0,0,ZoneOffset.UTC).toInstant()
        val oldDate = "Sun, 19 Jan 2021 00:00:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(newDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.howMuchAgo(oldDate)

        //Assert
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.DAY))
        }
    }

    @Test
    fun test_HoursAgo(){
        //Given
        val newDate =
            ZonedDateTime.of(2021,12,20,11,0,0,0,ZoneOffset.UTC).toInstant()
        val oldDate = "Sun, 20 Dec 2021 10:00:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(newDate, ZoneOffset.UTC))

        //When
        val value = dateTimeFormatter.howMuchAgo(oldDate)

        //Assert
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.HOUR))
        }
    }

    @Test
    fun test_MinutesAgo(){
        //Given
        val newDate =
            ZonedDateTime.of(2021,12,20,10,20,0,0,ZoneOffset.UTC).toInstant()
        val oldDate = "Sun, 20 Dec 2021 10:19:00 GMT"
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(newDate, ZoneOffset.UTC))
        println(newDate)

        //When
        val value = dateTimeFormatter.howMuchAgo(oldDate)

        //Assert
        assertThat(value).apply {
            isEqualTo(Pair(1, Ago.MINUTE))
        }
    }

}