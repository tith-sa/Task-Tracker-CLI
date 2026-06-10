package org.example.utils

import java.time.LocalDate
import java.time.format.DateTimeParseException

fun String.isBefore(): Boolean {
    return try {
        LocalDate.parse(this).isBefore(LocalDate.now())
    } catch (e: DateTimeParseException) {
        false
    }
}