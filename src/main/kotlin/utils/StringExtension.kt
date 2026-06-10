package org.example.utils

import java.time.LocalDate
import java.time.format.DateTimeParseException

fun String.isBefore(date: LocalDate): Boolean {
    return try {
        LocalDate.parse(this).isBefore(date)
    } catch (e: DateTimeParseException) {
        false
    }
}