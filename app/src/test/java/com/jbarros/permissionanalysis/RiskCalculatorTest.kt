package com.jbarros.permissionanalysis

import com.jbarros.permissionanalysis.utils.RiskCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class RiskCalculatorTest() {

    private val riskCalculator = RiskCalculator()

    @Test
    fun calculateRisk_allDangerousAndSpecialPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.INTERNET",
            "android.permission.SEND_SMS",
            "android.permission.BLUETOOTH",
            "android.permission.NFC",
            "android.permission.ACCESS_FINE_LOCATION"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)
        print(risk)

        // Assert
        assertEquals(5, risk)
    }

    @Test
    fun calculateRisk_dangerousAndInternetPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.INTERNET",
            "android.permission.SEND_SMS",
            "android.permission.ACCESS_FINE_LOCATION"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)
        print(risk)
        // Assert
        assertEquals(4, risk)
    }

    @Test
    fun calculateRisk_dangerousAndBluetoothPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.BLUETOOTH",
            "android.permission.NFC",
            "android.permission.ACCESS_FINE_LOCATION"
        )

        // Act
        var risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(3, risk)
    }

    @Test
    fun calculateRisk_onlyDangerousPermissionsX2_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        )

        // Act
        var risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(2, risk)
    }

    @Test
    fun calculateRisk_onlyDangerousPermissions_granted() {
        // Arrange
        val grantedPermissions = listOf(
            "android.permission.ACCESS_FINE_LOCATION"
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
            "android.permission.QUERY_ALL_PACKAGES"
        )

        // Act
        val risk = riskCalculator.calculateRisk(grantedPermissions)

        // Assert
        assertEquals(0, risk)
    }

}