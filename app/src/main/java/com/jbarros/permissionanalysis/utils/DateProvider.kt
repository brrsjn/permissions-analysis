package com.jbarros.permissionanalysis.utils

import java.text.SimpleDateFormat
import java.util.Calendar

class DateProvider {

    // Stores the current date and time when the class is constructed
    private val currentDateTime: String = getCurrentDateTime()

    private fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(calendar.time)
    }

    // Function to get the stored current date and time
    fun getDateTime(): String {
        return currentDateTime
    }
}