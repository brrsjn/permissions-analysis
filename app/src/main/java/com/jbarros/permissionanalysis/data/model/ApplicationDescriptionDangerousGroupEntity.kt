package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "application_description_dangerous_group",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationDescriptionEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("application_description_uid"),
        ),
        ForeignKey(
            entity = DangerousGroupEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("dangerous_group_uid"),
        )
    ]
)
data class ApplicationDescriptionDangerousGroupEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "application_description_uid") val applicationDescriptionUid: Int = 0,
    @ColumnInfo(name = "dangerous_group_uid") val dangerousGroupUid: Int = 0,
)