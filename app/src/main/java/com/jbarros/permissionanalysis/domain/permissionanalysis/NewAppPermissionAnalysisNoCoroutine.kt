package com.jbarros.permissionanalysis.domain.permissionanalysis

import android.util.Log
import com.jbarros.permissionanalysis.data.ApplicationPermissionRepository
import com.jbarros.permissionanalysis.data.PermissionRepository
import com.jbarros.permissionanalysis.data.applications.PermissionAnalysisRepository

import com.jbarros.permissionanalysis.data.model.PermissionEntity
import com.jbarros.permissionanalysis.domain.model.*

import com.jbarros.permissionanalysis.utils.DateProvider
import com.jbarros.permissionanalysis.utils.RiskCalculator
import javax.inject.Inject

class NewAppPermissionAnalysisNoCoroutine @Inject constructor(
    private val permissionAnalysisRepository: PermissionAnalysisRepository,
    private val permissionRepository: PermissionRepository,
    private val riskValueCalculator: RiskCalculator,
    private val applicationPermissionRepository: ApplicationPermissionRepository
) {
    //Pendiente: Revisar los permisos que yo habia guardado pero que ya no estan en la app
     operator fun invoke(application: Application) {
        val dateProvider = DateProvider()

        val grantedPermission =
            permissionAnalysisRepository.getGrantedPermission(packageName = application.packageName)
        val requestedPermission =
            permissionAnalysisRepository.getRequestedPermission(packageName = application.packageName)

        val permissionByApplicationId =
            applicationPermissionRepository.getAllPermissionByApplicationId(applicationId = application.id)
                .map { it.toApplicationPermission() }

        if (permissionByApplicationId.isEmpty()) {
            Log.d("NEWPAPPPERMISSIONANAL", "Es vacio el permissionByApplicationId")
            if (requestedPermission.isNotEmpty()) {
                for (rP in requestedPermission) {
                    Log.d("requestedPermission", "requestedPermission: $rP")
                    var isGranted = false
                    if (rP in grantedPermission) {
                        isGranted = true
                    }
                    var permissionIDByrepo = permissionRepository.getIdByName(rP)
                    if(permissionIDByrepo == 0) {
                        val newPermission = PermissionEntity(name = rP, constantName = rP, protectionLevel = "no-level")
                        permissionRepository.insertPermission(newPermission)
                        permissionIDByrepo = permissionRepository.getIdByName(rP)

                    }
                    val applicationPermission = ApplicationPermission(
                        applicationUid = application.id,
                        permissionId = permissionIDByrepo,
                        isGranted = isGranted,
                        createdAt = dateProvider.getDateTime()
                    )
                    applicationPermissionRepository.insert(applicationPermission.toApplicationPermissionEntity())
                }
            }
        } else {
            Log.d("NEWPAPPPERMISSIONANA", "No es vacio permissionByApplicationId")
            val applicationPermissionsList =
                permissionRepository.getAllByApplicationId(applicationId = application.id)
            for (rP in requestedPermission) {
                val matchingPermission =
                    applicationPermissionsList.firstOrNull { it.toPermission().constantName == rP }
                var isGranted = false
                if (rP in grantedPermission) {
                    isGranted = true
                }
                Log.d("MATCHPERMISSION", "El largo del listado es: ${applicationPermissionsList.size}")
                Log.d("MATCHPERMISSION", "$matchingPermission")
                var permissionId: Int
                if (matchingPermission != null) {
                    permissionId = matchingPermission.toPermission().id
                    var matchingApplicationPermission =
                        permissionByApplicationId.firstOrNull { it.permissionId == permissionId }
                    if (matchingApplicationPermission != null) {
                        if (isGranted != matchingApplicationPermission.isGranted) {
                            matchingApplicationPermission.isGranted = isGranted
                            applicationPermissionRepository.update(matchingApplicationPermission.toApplicationPermissionEntity())
                        }
                    }
                } else {
                    var permissionIDByrepo = permissionRepository.getIdByName(rP)
                    if(permissionIDByrepo == 0) {
                        val newPermission = PermissionEntity(name = rP, constantName = rP, protectionLevel = "no-level")
                        permissionRepository.insertPermission(newPermission)
                        permissionIDByrepo = permissionRepository.getIdByName(rP)
                    }
                    Log.d("permissionIDByrepo", "permissionIDByrepo:${permissionIDByrepo} applicationUid: ${application.id}")
                    val applicationPermission = ApplicationPermission(
                        applicationUid = application.id,
                        permissionId = permissionIDByrepo,
                        isGranted = isGranted,
                        createdAt = dateProvider.getDateTime()
                    )
                    applicationPermissionRepository.insert(applicationPermission.toApplicationPermissionEntity())
                }
            }
        }
        val dangerousPermissions = permissionRepository.getDangerous().map { it.constantName }
        val riskValue = riskValueCalculator.calculateRisk(grantedPermission, dangerousPermissions, false)
        println("El siguiente informe es para la aplicacion: ${application.appName})")
        val riskValueRequested =
            riskValueCalculator.calculateRisk(requestedPermission, dangerousPermissions, true)
        var createdAt = dateProvider.getDateTime()
        val permissionAnalysis = PermissionAnalysis(
            applicationUid = application.id,
            createdAt = createdAt,
            riskScore = riskValue,
            riskScoreRequested = riskValueRequested
        )
        permissionAnalysisRepository.insertPermissionAnalysis(permissionAnalysis.toPermissionAnalysisEntity())

        return
    }
}