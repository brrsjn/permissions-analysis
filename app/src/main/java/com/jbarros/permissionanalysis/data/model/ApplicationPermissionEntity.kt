package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "application_permission",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("application_uid"),
        ),
        ForeignKey(
            entity = PermissionEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("permission_uid"),
        )
    ]
)
data class ApplicationPermissionEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "application_uid") val applicationUid: Int,
    @ColumnInfo(name = "permission_uid") val permissionId: Int,
    @ColumnInfo(name = "is_granted") val isGranted: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String?
)