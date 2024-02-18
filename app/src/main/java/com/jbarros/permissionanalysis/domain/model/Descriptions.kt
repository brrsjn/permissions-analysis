package com.jbarros.permissionanalysis.domain.model

import com.jbarros.permissionanalysis.network.model.DescriptionsData
import com.jbarros.permissionanalysis.network.model.PrivacyPoliciesData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Descriptions(
    val STORAGE: Boolean = false,
    val CONTACTS: Boolean = false,
    val LOCATION: Boolean = false,
    val CAMERA: Boolean = false,
    val MICROPHONE: Boolean = false,
    val SMS: Boolean = false,
    val CALL_LOG: Boolean = false,
    val PHONE: Boolean = false,
    val CALENDAR: Boolean = false,
    val SETTINGS: Boolean = false,
    val TASKS: Boolean = false,
)

fun DescriptionsData.toDescriptions(): Descriptions = Descriptions(
    STORAGE = STORAGE,
    CONTACTS = CONTACTS,
    LOCATION = LOCATION,
    CAMERA = CAMERA,
    MICROPHONE = MICROPHONE,
    SMS = SMS,
    CALL_LOG = CALL_LOG,
    PHONE = PHONE,
    CALENDAR = CALENDAR,
    SETTINGS = SETTINGS,
    TASKS = TASKS,
)