package com.jbarros.permissionanalysis.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "permission",
    foreignKeys = [
        ForeignKey(
            entity = SensitiveDataCategoryEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("sensitive_data_category_uid"),
        ),
        ForeignKey(
            entity = DangerousGroupEntity::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("dangerous_group_uid"),
        )
    ]
)
data class PermissionEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "protection_level") val protectionLevel: String = "",
    @ColumnInfo(name = "constant_name") val constantName: String = "",
    @ColumnInfo(name = "sensitive_data_category_uid") val sensitiveDataCategoryUid: Int = 0,
    @ColumnInfo(name = "dangerous_group_uid") val dangerousGroupUid: Int = 0
)