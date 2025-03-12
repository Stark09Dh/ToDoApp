package com.appsv.todoapp.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatToReadableDate(date : Long) : String{
    val dateFormat = SimpleDateFormat("dd MMMM, hh:mm a, yyyy", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    return dateFormat.format(date)
}