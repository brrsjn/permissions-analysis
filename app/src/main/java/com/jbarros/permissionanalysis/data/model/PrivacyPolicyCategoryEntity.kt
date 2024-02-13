package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "privacy_policy_category",
    foreignKeys = [
        ForeignKey(
            entity = PrivacyPolicyEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("privacy_policy_id"),
        ),
        ForeignKey(
            entity = PrivacyCategoryEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("privacy_category_id"),
        )
    ]
)
data class PrivacyPolicyCategoryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "privacy_policy_id") val privacyPolicyId: Int,
    @ColumnInfo(name = "privacy_category_id") val privacyCategoryId: Int
)