package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "permission_change",
    foreignKeys = [
        ForeignKey(
            entity = PermissionAnalysisEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("permission_analysis_uid"),
        ),
        ForeignKey(
            entity = ApplicationPermissionEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("application_permission_uid"),
        )
    ]
)
data class PermissionChangeEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "new_state") val newState: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: String = "",
    @ColumnInfo(name = "permission_analysis_uid") val permissionAnalysisUid: Int = 0,
    @ColumnInfo(name = "application_permission_uid") val applicationPermissionUid: Int = 0
) {
    companion object {
        const val GRANTED_PERMISSION = "GRANTED"
        const val NOT_GRANTED_PERMISSION = "NOT GRANTED"
        const val DELETED = "DELETED"
    }
}