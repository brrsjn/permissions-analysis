package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "permission_analysis",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("application_uid"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PermissionAnalysisEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "application_uid") val applicationUid: Int = 0,
    @ColumnInfo(name = "created_at") val createdAt: String = "",
    @ColumnInfo(name = "risk_score") val riskScore: Int = 0,
    @ColumnInfo(name = "risk_score_requested") val riskScoreRequested: Int = 0,
    @ColumnInfo(name = "granted_permission_count") val grantedPermissionCount: Int = 0,
    @ColumnInfo(name = "permission_count") val permissionCount: Int = 0,
)