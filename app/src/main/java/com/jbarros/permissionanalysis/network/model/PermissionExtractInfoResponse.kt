package com.jbarros.permissionanalysis.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PermissionExtractInfoData(
    @Json(name = "permission_name") val permission_name: String,
    @Json(name = "description") val description: String,
    @Json(name = "protection_level") val protection_level: String?,
    @Json(name = "constant_permission_name") val constant_permission_name: String
)

@JsonClass(generateAdapter = true)
data class PermissionExtractInfoResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: List<PermissionExtractInfoData>,
)


