package com.jbarros.permissionanalysis.utils

import android.util.Log


class RiskCalculator() {

    private val permissionsMap = mapOf(
        "android.permission.GET_ACCOUNTS" to PermissionInfo("ACCOUNT_INFORMATION", "GET_ACCOUNTS"),
        "android.permission.READ_CALENDAR" to PermissionInfo("CALENDAR", "READ_CALENDAR"),
        "android.permission.READ_CALL_LOG" to PermissionInfo("Calling information", "READ_CALL_LOG"),
        "android.permission.READ_CONTACTS" to PermissionInfo("Contacts and profile", "READ_CONTACTS"),
        "android.permission.ACCESS_FINE_LOCATION" to PermissionInfo("Location", "ACCESS_FINE_LOCATION"),
        "android.permission.ACCESS_COARSE_LOCATION" to PermissionInfo("Location", "ACCESS_COARSE_LOCATION"),
        "android.permission.ACCESS_MEDIA_LOCATION" to PermissionInfo("Location", "ACCESS_MEDIA_LOCATION"),
        "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" to PermissionInfo("Location", "ACCESS_LOCATION_EXTRA_COMMANDS"),
        "android.permission.CAMERA" to PermissionInfo("Media", "CAMERA"),
        "android.permission.RECORD_AUDIO" to PermissionInfo("Media", "RECORD_AUDIO"),
        "android.permission.READ_MEDIA_VIDEO" to PermissionInfo("Media", "READ_MEDIA_VIDEO"),
        "android.permission.READ_MEDIA_IMAGES" to PermissionInfo("Media", "READ_MEDIA_IMAGES"),
        "android.permission.READ_MEDIA_AUDIO" to PermissionInfo("Media", "READ_MEDIA_AUDIO"),
        "android.permission.READ_SMS" to PermissionInfo("Messages", "READ_SMS"),
        "android.permission.RECEIVE_SMS" to PermissionInfo("Messages", "RECEIVE_SMS"),
        "com.android.voicemail.permission.READ_VOICEMAIL" to PermissionInfo("Messages", "READ_VOICEMAIL"),
        "android.permission.RECEIVE_MMS" to PermissionInfo("Messages", "RECEIVE_MMS"),
        "android.permission.RECEIVE_WAP_PUSH" to PermissionInfo("Messages", "RECEIVE_WAP_PUSH"),
        "android.permission.ACCESS_NETWORK_STATE" to PermissionInfo("Network information", "ACCESS_NETWORK_STATE"),
        "android.permission.ACCESS_WIFI_STATE" to PermissionInfo("Network information", "ACCESS_WIFI_STATE"),
        "android.permission.READ_PHONE_STATE" to PermissionInfo("Phone information", "READ_PHONE_STATE"),
        "android.permission.READ_EXTERNAL_STORAGE" to PermissionInfo("External storage data", "READ_EXTERNAL_STORAGE"),
        "android.permission.MANAGE_DOCUMENTS" to PermissionInfo("External storage data", "MANAGE_DOCUMENTS")
        // Agregar más permisos si son necesarios
    )

    fun calculateRisk(permissionList: List<String>): Int {
        val hasInternet = permissionList.contains(android.Manifest.permission.INTERNET)

        val hasSendSms = permissionList.contains(android.Manifest.permission.SEND_SMS)
        val hasBluetooth = permissionList.contains(android.Manifest.permission.BLUETOOTH)
        val hasNfc = permissionList.contains(android.Manifest.permission.NFC)

        var hasDangerousPermission = false

        var dangerousPermissionCount = 0

        for (permissionElement in permissionList) {
            val permissionInfo = getPermissionInfo(permissionElement)
            if (permissionInfo != null) {
                hasDangerousPermission = true
                dangerousPermissionCount += 1
            }
        }

        var riskValue = 0

        when {
            hasDangerousPermission && ((hasInternet || hasSendSms) && (hasBluetooth || hasNfc)) -> riskValue = 5
            hasDangerousPermission && (hasInternet || hasSendSms) -> riskValue = 4
            hasDangerousPermission && (hasBluetooth || hasNfc) -> riskValue = 3
            hasDangerousPermission && dangerousPermissionCount > 1 -> riskValue = 2
            hasDangerousPermission -> riskValue = 1
        }

        return riskValue
    }

    private fun getPermissionInfo(constantName: String): PermissionInfo? {
        return permissionsMap[constantName]
    }

}