package com.culturetrip.article.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * return format dd MMM, yyyy like 18 May, 2020
 */
fun Date.formatDisplayDate(dateString : String) : String {
    val inPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    val outPattern = "dd MMM, yyyy"

    val inFormat = SimpleDateFormat(inPattern, Locale.getDefault())
    val outFormat = SimpleDateFormat(outPattern, Locale.getDefault())

    val inDate = inFormat.parse(dateString)
    val outDate = outFormat.format(inDate)

    return outDate
}

