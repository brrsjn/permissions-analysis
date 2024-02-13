package com.jbarros.permissionanalysis.utils

class SensitivePermissionInfo {
    val permissionsMap = mapOf(
        "android.permission.GET_ACCOUNTS" to PermissionInfo("ACCOUNT_INFORMATION", "GET_ACCOUNTS"),
        "android.permission.READ_CALENDAR" to PermissionInfo("CALENDAR", "READ_CALENDAR"),
        "android.permission.READ_CALL_LOG" to PermissionInfo(
            "CALLING_INFORMATION",
            "READ_CALL_LOG"
        ),
        "android.permission.READ_CONTACTS" to PermissionInfo(
            "Contacts and profile",
            "READ_CONTACTS"
        ),
        "android.permission.ACCESS_FINE_LOCATION" to PermissionInfo(
            "LOCATION",
            "ACCESS_FINE_LOCATION"
        ),
        "android.permission.ACCESS_COARSE_LOCATION" to PermissionInfo(
            "LOCATION",
            "ACCESS_COARSE_LOCATION"
        ),
        "android.permission.ACCESS_MEDIA_LOCATION" to PermissionInfo(
            "LOCATION",
            "ACCESS_MEDIA_LOCATION"
        ),
        "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" to PermissionInfo(
            "LOCATION",
            "ACCESS_LOCATION_EXTRA_COMMANDS"
        ),
        "android.permission.CAMERA" to PermissionInfo("MEDIA", "CAMERA"),
        "android.permission.RECORD_AUDIO" to PermissionInfo("MEDIA", "RECORD_AUDIO"),
        "android.permission.READ_MEDIA_VIDEO" to PermissionInfo("MEDIA", "READ_MEDIA_VIDEO"),
        "android.permission.READ_MEDIA_IMAGES" to PermissionInfo("MEDIA", "READ_MEDIA_IMAGES"),
        "android.permission.READ_MEDIA_AUDIO" to PermissionInfo("MEDIA", "READ_MEDIA_AUDIO"),
        "android.permission.READ_SMS" to PermissionInfo("MESSAGES", "READ_SMS"),
        "android.permission.RECEIVE_SMS" to PermissionInfo("MESSAGES", "RECEIVE_SMS"),
        "com.android.voicemail.permission.READ_VOICEMAIL" to PermissionInfo(
            "MESSAGES",
            "READ_VOICEMAIL"
        ),
        "android.permission.RECEIVE_MMS" to PermissionInfo("MESSAGES", "RECEIVE_MMS"),
        "android.permission.RECEIVE_WAP_PUSH" to PermissionInfo("MESSAGES", "RECEIVE_WAP_PUSH"),
        "android.permission.ACCESS_NETWORK_STATE" to PermissionInfo(
            "NETWORK_INFORMATION",
            "ACCESS_NETWORK_STATE"
        ),
        "android.permission.ACCESS_WIFI_STATE" to PermissionInfo(
            "NETWORK_INFORMATION",
            "ACCESS_WIFI_STATE"
        ),
        "android.permission.READ_PHONE_STATE" to PermissionInfo(
            "PHONE_INFORMATION",
            "READ_PHONE_STATE"
        ),
        "android.permission.READ_EXTERNAL_STORAGE" to PermissionInfo(
            "EXTERNAL_STORAGE_DATA",
            "READ_EXTERNAL_STORAGE"
        ),
        "android.permission.MANAGE_DOCUMENTS" to PermissionInfo(
            "EXTERNAL_STORAGE_DATA",
            "MANAGE_DOCUMENTS"
        )
    )

    fun getCategory(constantName: String): String? {
        return permissionsMap[constantName]?.category
    }
}

class PermissionInfo(val category: String, val permissionName: String)
