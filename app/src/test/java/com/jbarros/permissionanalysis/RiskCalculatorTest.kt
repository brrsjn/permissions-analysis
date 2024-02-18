package com.jbarros.permissionanalysis

import com.jbarros.permissionanalysis.utils.RiskCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class RiskCalculatorTest {

    private val riskCalculator = RiskCalculator()
    val dangerousPermissions = listOf(
        "android.permission.ACCEPT_HANDOVER",
        "android.permission.ACCESS_BACKGROUND_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_MEDIA_LOCATION",
        "android.permission.ACTIVITY_RECOGNITION",
        "com.android.voicemail.permission.ADD_VOICEMAIL",
        "android.permission.ANSWER_PHONE_CALLS",
        "android.permission.BLUETOOTH_ADVERTISE",
        "android.permission.BLUETOOTH_CONNECT",
        "android.permission.BLUETOOTH_SCAN",
        "android.permission.BODY_SENSORS",
        "android.permission.BODY_SENSORS_BACKGROUND",
        "android.permission.CALL_PHONE",
        "android.permission.CAMERA",
        "android.permission.GET_ACCOUNTS",
        "android.permission.NEARBY_WIFI_DEVICES",
        "android.permission.POST_NOTIFICATIONS",
        "android.permission.PROCESS_OUTGOING_CALLS",
        "android.permission.READ_CALENDAR",
        "android.permission.READ_CALL_LOG",
        "android.permission.READ_CONTACTS",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.READ_MEDIA_AUDIO",
        "android.permission.READ_MEDIA_IMAGES",
        "android.permission.READ_MEDIA_VIDEO",
        "android.permission.READ_MEDIA_VISUAL_USER_SELECTED",
        "android.permission.READ_PHONE_NUMBERS",
        "android.permission.READ_PHONE_STATE",
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_MMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.RECEIVE_WAP_PUSH",
        "android.permission.RECORD_AUDIO",
        "android.permission.SEND_SMS",
        "android.permission.USE_SIP",
        "android.permission.UWB_RANGING",
        "android.permission.WRITE_CALENDAR",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.WRITE_CONTACTS",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )



    @Test
    fun calculateRisk_allDangerousAndSpecialPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.INTERNET",
            "android.permission.SEND_SMS",
            "android.permission.BLUETOOTH",
            "android.permission.NFC"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(5, risk)
    }

    @Test
    fun calculateRisk_dangerousAndInternetPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.INTERNET",
            "android.permission.SEND_SMS"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(4, risk)
    }

    @Test
    fun calculateRisk_dangerousAndBluetoothPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.BLUETOOTH",
            "android.permission.NFC"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(3, risk)
    }

    @Test
    fun calculateRisk_onlyDangerousPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.SEND_SMS"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(1, risk)
    }

    @Test
    fun calculateRisk_noDangerousPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.ACCESS_FINE_LOCATION"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(0, risk)
    }

    // You can continue writing more test cases to cover all your branches and scenarios...
}