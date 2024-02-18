package com.jbarros.permissionanalysis.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DescriptionsData(
    @Json(name = "STORAGE") val STORAGE: Boolean,
    @Json(name = "CONTACTS") val CONTACTS: Boolean,
    @Json(name = "LOCATION") val LOCATION: Boolean,
    @Json(name = "CAMERA") val CAMERA: Boolean,
    @Json(name = "MICROPHONE") val MICROPHONE: Boolean,
    @Json(name = "SMS") val SMS: Boolean,
    @Json(name = "CALL_LOG") val CALL_LOG: Boolean,
    @Json(name = "PHONE") val PHONE: Boolean,
    @Json(name = "CALENDAR") val CALENDAR: Boolean,
    @Json(name = "SETTINGS") val SETTINGS: Boolean,
    @Json(name = "TASKS") val TASKS: Boolean,
    )

@JsonClass(generateAdapter = true)
data class DescriptionsResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: DescriptionsData,
)


