package com.omvp.commons

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

import java.util.Calendar

/**
 * Created by Angel on 27/09/2017.
 */

object LocalDateUtils {

    fun toCalendar(localDate: LocalDate): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = toMilliseconds(localDate)
        return calendar
    }

    fun toMilliseconds(localDate: LocalDate): Long {
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun toMilliseconds(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun toLocalDate(milliseconds: Long): LocalDate {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun toLocalDateTime(milliseconds: Long): LocalDateTime {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

}
