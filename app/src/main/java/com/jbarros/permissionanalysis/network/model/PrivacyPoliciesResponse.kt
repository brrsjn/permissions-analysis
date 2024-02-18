package com.jbarros.permissionanalysis.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrivacyPoliciesData(
    @Json(name = "link") val link: String,
    @Json(name = "policy_introductory") val policy_introductory: String,
    @Json(name = "first_party_collection_and_use") val first_party_collection_and_use: String,
    @Json(name = "cookies_and_similar_technologies") val cookies_and_similar_technologies: String,
    @Json(name = "data_retention") val data_retention: String,
    @Json(name = "third_party_share_and_collection") val third_party_share_and_collection: String,
    @Json(name = "data_security") val data_security: String,
    @Json(name = "international_data_transfer") val international_data_transfer: String,
    @Json(name = "user_right_and_control") val user_right_and_control: String,
    @Json(name = "specific_audiences") val specific_audiences: String,
    @Json(name = "policy_change") val policy_change: String,
    @Json(name = "policy_contact_information") val policy_contact_information: String,
    )

@JsonClass(generateAdapter = true)
data class PrivacyPoliciesResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: PrivacyPoliciesData,
)


