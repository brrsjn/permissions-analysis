package com.jbarros.permissionanalysis.domain.model

import com.jbarros.permissionanalysis.data.model.PermissionAnalysisEntity

data class PermissionAnalysis(
    val id: Int = 0,
    val applicationUid: Int = 0,
    val createdAt: String = "",
    val riskScore: Int = 0,
    val riskScoreRequested: Int = 0
)


fun PermissionAnalysis.toPermissionAnalysisEntity(): PermissionAnalysisEntity = PermissionAnalysisEntity(
    uid = id,
    applicationUid = applicationUid,
    createdAt = createdAt,
    riskScore = riskScore,
    riskScoreRequested = riskScoreRequested
)

fun PermissionAnalysisEntity.toApplication(): PermissionAnalysis = PermissionAnalysis(
    id = uid,
    applicationUid = applicationUid,
    createdAt = createdAt,
    riskScore = riskScore,
    riskScoreRequested = riskScoreRequested
)