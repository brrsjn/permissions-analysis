package com.jbarros.permissionanalysis.network

import com.jbarros.permissionanalysis.network.model.PermissionExtractInfoResponse
import retrofit2.http.GET


interface
PermissionExtractInfoService {

    @GET("/Prod/")
    suspend fun getListPermission(): List<PermissionExtractInfoResponse>
}