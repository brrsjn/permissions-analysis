package com.jbarros.permissionanalysis.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jbarros.permissionanalysis.data.model.*

@Database(
    entities = [ApplicationCategoryEntity::class, ApplicationDescriptionDangerousGroupEntity::class, ApplicationDescriptionEntity::class,
        ApplicationEntity::class, ApplicationPermissionEntity::class, DangerousGroupEntity::class, PermissionAnalysisEntity::class,
        PermissionChangeEntity::class, PermissionEntity::class, PrivacyCategoryEntity::class, PrivacyPolicyCategoryEntity::class,
        PrivacyPolicyEntity::class, SensitiveDataCategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun applicationDao(): ApplicationDao
    abstract fun applicationPermissionDao(): ApplicationPermissionDao
    abstract fun permissionDao(): PermissionDao
    abstract fun permissionAnalysisDao(): PermissionAnalysisDao
    abstract fun sensitiveDataCategoryDao(): SensitiveDataCategoryDao
    abstract fun dangerousGroupDao(): DangerousGroupDao
}