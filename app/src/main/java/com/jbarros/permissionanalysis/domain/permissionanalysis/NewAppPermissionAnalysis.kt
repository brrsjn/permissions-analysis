package com.jbarros.permissionanalysis.domain.permissionanalysis

import android.util.Log
import com.jbarros.permissionanalysis.data.*
import com.jbarros.permissionanalysis.data.applications.PermissionAnalysisRepository

import com.jbarros.permissionanalysis.data.model.PermissionEntity

import com.jbarros.permissionanalysis.domain.model.*
import com.jbarros.permissionanalysis.utils.DateProvider
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import com.jbarros.permissionanalysis.utils.RiskCalculator
import com.jbarros.permissionanalysis.utils.SensitivePermissionInfo
import javax.inject.Inject

class NewAppPermissionAnalysis @Inject constructor(
    private val permissionAnalysisRepository: PermissionAnalysisRepository,
    private val permissionRepository: PermissionRepository,
    private val riskValueCalculator: RiskCalculator,
    private val applicationPermissionRepository: ApplicationPermissionRepository,
    private val dangerousGroupRepository: DangerousGroupRepository,
    private val sensitiveDataCategoryRepository: SensitiveDataCategoryRepository,
    private val packageManagerSource: PackageManagerSource,
    private val sensitivePermissionInfo: SensitivePermissionInfo,
    private val permissionChangeRepository: PermissionChangeRepository
) {
    //Pendiente: Revisar los permisos que yo habia guardado pero que ya no estan en la app
    suspend operator fun invoke(application: Application) {
        Log.d("TESTANALYSIS", "Iniciando")

        val dateProvider = DateProvider()

        val grantedPermission =
            permissionAnalysisRepository.getGrantedPermission(packageName = application.packageName)
        val requestedPermission =
            permissionAnalysisRepository.getRequestedPermission(packageName = application.packageName)

        Log.d("TESTANALYSIS", "grantedPermission: ${grantedPermission.size}")
        Log.d("TESTANALYSIS", "requestedPermission: ${requestedPermission.size}")

        val permissionByApplicationId =
            applicationPermissionRepository.getAllPermissionByApplicationId(applicationId = application.id)
                .map { it.toApplicationPermission() }

        var isChangedPermission = false
        var permissionChange = mutableListOf<PermissionChange>()

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

                    if (permissionIDByrepo == 0) {
                        val permissionGroup =
                            packageManagerSource.getPermissionGroup(rP) ?: "no-group"
                        var dangerousGroup = dangerousGroupRepository.getOne(permissionGroup)
                        if (dangerousGroup == null) {
                            dangerousGroupRepository.insert(permissionGroup)
                            dangerousGroup = dangerousGroupRepository.getOne(permissionGroup)
                        }
                        val categoryName = sensitivePermissionInfo.getCategory(rP) ?: "no-category"

                        val sensitiveDataCategoryItem =
                            sensitiveDataCategoryRepository.getOne(categoryName)
                        val newPermission = PermissionEntity(
                            name = rP,
                            constantName = rP,
                            protectionLevel = "no-level",
                            dangerousGroupUid = dangerousGroup.uid,
                            sensitiveDataCategoryUid = sensitiveDataCategoryItem.uid
                        )
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
                Log.d(
                    "MATCHPERMISSION",
                    "El largo del listado es: ${applicationPermissionsList.size}"
                )
                Log.d("MATCHPERMISSION", "$matchingPermission")
                var permissionId: Int
                if (matchingPermission != null) {
                    permissionId = matchingPermission.toPermission().id
                    Log.d("MATCHPERMISSION", "$permissionId")
                    var matchingApplicationPermission =
                        permissionByApplicationId.firstOrNull { it.permissionId == permissionId }
                    Log.d("MATCHPERMISSION", "$matchingApplicationPermission")
                    if (matchingApplicationPermission != null) {
                        if (isGranted != matchingApplicationPermission.isGranted) {
                            Log.d("TESTANALYSIS", "identifico cambio de permisos")
                            matchingApplicationPermission.isGranted = isGranted
                            applicationPermissionRepository.update(matchingApplicationPermission.toApplicationPermissionEntity())
                            isChangedPermission =  true
                            val permissionChangeElement = PermissionChange(newState = isGranted, createdAt = dateProvider.getDateTime(), applicationPermissionUid = matchingApplicationPermission.id)
                            permissionChange.add(permissionChangeElement)
                            //Dado que permission change necesita el Permission analysis id, entonces, esto se hará hasta abajo
                        }
                    }
                } else {
                    var permissionIDByrepo = permissionRepository.getIdByName(rP)
                    if (permissionIDByrepo == 0) {
                        val permissionGroup =
                            packageManagerSource.getPermissionGroup(rP) ?: "no-group"
                        var dangerousGroup = dangerousGroupRepository.getOne(permissionGroup)
                        if (dangerousGroup == null) {
                            dangerousGroupRepository.insert(permissionGroup)
                            dangerousGroup = dangerousGroupRepository.getOne(permissionGroup)
                        }
                        val categoryName = sensitivePermissionInfo.getCategory(rP) ?: "no-category"

                        val sensitiveDataCategoryItem =
                            sensitiveDataCategoryRepository.getOne(categoryName)
                        val newPermission = PermissionEntity(
                            name = rP,
                            constantName = rP,
                            protectionLevel = "no-level",
                            dangerousGroupUid = dangerousGroup.uid,
                            sensitiveDataCategoryUid = sensitiveDataCategoryItem.uid
                        )
                        permissionRepository.insertPermission(newPermission)
                        permissionIDByrepo = permissionRepository.getIdByName(rP)
                    }
                    Log.d(
                        "permissionIDByrepo",
                        "permissionIDByrepo:${permissionIDByrepo} applicationUid: ${application.id}"
                    )
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
        val riskValue =
            riskValueCalculator.calculateRisk(grantedPermission)
        println("El siguiente informe es para la aplicacion: ${application.appName})")

        val riskValueRequested =
            riskValueCalculator.calculateRisk(requestedPermission)
        println("El valor de riesgo es: $riskValueRequested")
        var createdAt = dateProvider.getDateTime()
        val permissionAnalysis = PermissionAnalysis(
            applicationUid = application.id,
            createdAt = createdAt,
            riskScore = riskValue,
            riskScoreRequested = riskValueRequested
        )
        val id =  permissionAnalysisRepository.insertPermissionAnalysis(permissionAnalysis.toPermissionAnalysisEntity())
        Log.d("TESTANALYSIS", "Ojala imprima un id: $id")
        if (isChangedPermission) {
            Log.d("TESTANALYSIS", "hay cambio")
            for (i in permissionChange) {
                i.permissionAnalysisUid = id.toInt()
                permissionChangeRepository.insert(i.toPermissionChangeEntity())
                Log.d("TESTANALYSIS", "insertó un cambio")
            }

        }

        return
    }
}