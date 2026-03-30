package com.jeevan.weatherapp.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formats "2026-03-29" → "Sun, Mar 29"
 */
fun formatDate(dateStr: String): String {
    return try {
        val date = LocalDate.parse(dateStr)
        date.format(DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH))
    } catch (_: Exception) {
        dateStr
    }
}