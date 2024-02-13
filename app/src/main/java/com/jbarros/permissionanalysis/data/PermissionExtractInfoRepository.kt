package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.network.PermissionExtractInfoService
import com.jbarros.permissionanalysis.network.model.PermissionExtractInfoResponse
import javax.inject.Inject

class PermissionExtractInfoRepository @Inject constructor(
    private val permissionExtractInfoService: PermissionExtractInfoService
) {
    suspend fun getPermissionList(): List<PermissionExtractInfoResponse> {
        return permissionExtractInfoService.getListPermission()
    }
}