package com.jbarros.permissionanalysis.network

import com.jbarros.permissionanalysis.network.model.DescriptionsResponse
import com.jbarros.permissionanalysis.network.model.PermissionExtractInfoResponse
import com.jbarros.permissionanalysis.network.model.PrivacyPoliciesResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface
PermissionsAnalysisAPIService {

    @GET("permissions/")
    suspend fun getListPermission(): PermissionExtractInfoResponse

    @GET("descriptions/{packageName}")
    suspend fun getDescriptions(@Path("packageName") packageName: String): DescriptionsResponse

    @GET("privacyPolicies/{packageName}")
    suspend fun getPrivacyPolicies(@Path("packageName") packageName: String): PrivacyPoliciesResponse
}