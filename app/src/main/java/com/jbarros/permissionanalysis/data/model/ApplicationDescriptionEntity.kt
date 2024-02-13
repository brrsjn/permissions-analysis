package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "application_description",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("application_uid"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ApplicationDescriptionEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "created_at") val createdAt: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String?,
    @ColumnInfo(name = "application_uid") val applicationId: Int,
)
