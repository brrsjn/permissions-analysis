package com.jbarros.permissionanalysis.data.local

import androidx.room.*
import com.jbarros.permissionanalysis.data.model.PermissionChangeEntity
import com.jbarros.permissionanalysis.domain.model.PermissionChangeStrings

@Dao
interface PermissionChangeDao {
    @Insert
    fun insert(permissionChange: PermissionChangeEntity): Long

    @Query("SELECT * FROM permission_change pc INNER JOIN application_permission ap ON ap.uid = pc.application_permission_uid WHERE ap.application_uid = :applicationUid")
    fun getAllByAppId(applicationUid: Int): List<PermissionChangeEntity>
}