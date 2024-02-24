package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "privacy_policy",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("application_id"),
        )]
    )
data class PrivacyPolicyEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "link") val name: String,
    @ColumnInfo(name = "application_id") val applicationId: Int,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String?,
    )