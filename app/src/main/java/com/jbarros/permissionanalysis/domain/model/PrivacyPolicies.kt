package com.jbarros.permissionanalysis.domain.model

import com.jbarros.permissionanalysis.data.model.ApplicationEntity
import com.jbarros.permissionanalysis.network.model.PrivacyPoliciesData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class PrivacyPolicies(
    val link: String = "",
    val policy_introductory: String = "",
    val first_party_collection_and_use: String = "",
    val cookies_and_similar_technologies: String = "",
    val data_retention: String = "",
    val third_party_share_and_collection: String = "",
    val data_security: String = "",
    val international_data_transfer: String = "",
    val user_right_and_control: String = "",
    val specific_audiences: String = "",
    val policy_change: String = "",
    val policy_contact_information: String = "",
)

fun PrivacyPoliciesData.toPrivacyPolicies(): PrivacyPolicies = PrivacyPolicies(
    link = link,
    policy_introductory = policy_introductory,
    first_party_collection_and_use = first_party_collection_and_use,
    cookies_and_similar_technologies = cookies_and_similar_technologies,
    data_retention = data_retention,
    third_party_share_and_collection = third_party_share_and_collection,
    data_security = data_security,
    international_data_transfer = international_data_transfer,
    user_right_and_control = user_right_and_control,
    specific_audiences = specific_audiences,
    policy_change = policy_change,
    policy_contact_information = policy_contact_information,
)

