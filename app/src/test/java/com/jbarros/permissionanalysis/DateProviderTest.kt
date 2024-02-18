package com.jbarros.permissionanalysis

import com.jbarros.permissionanalysis.utils.DateProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateProviderTest {

    @Test
    fun `getDateTime returns the correct format`() {
        val dateProvider = DateProvider()
        val dateTime = dateProvider.getDateTime()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        // Try to parse the date string. If it fails, it means the format is incorrect.
        try {
            dateFormat.parse(dateTime)
            assertTrue("Date format is correct", true)
        } catch (e: Exception) {
            assertTrue("Date format is incorrect", false)
        }
    }

    @Test
    fun `getDateTime returns current date and time`() {
        val dateProvider = DateProvider()
        val providedDateTime = dateProvider.getDateTime()
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

        // The providedDateTime should be within a few milliseconds of the currentDateTime
        // Note: This test can be flaky if the timing is just at the turn of a second.
        // It is usually better to check the difference in milliseconds.
        assertTrue(providedDateTime.startsWith(currentDateTime.substring(0, 17)))
    }
}
