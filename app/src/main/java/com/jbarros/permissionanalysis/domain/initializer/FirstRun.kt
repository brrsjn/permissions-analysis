package com.jbarros.permissionanalysis.domain.initializer

import com.jbarros.permissionanalysis.data.*
import com.jbarros.permissionanalysis.domain.model.*
import com.jbarros.permissionanalysis.domain.permissionanalysis.NewAppPermissionAnalysis
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import com.jbarros.permissionanalysis.utils.SensitivePermissionInfo
import javax.inject.Inject

class FirstRun @Inject constructor(
    private val permissionExtractInfoRepository: PermissionExtractInfoRepository,
    private val permissionRepository: PermissionRepository,
    private val applicationRepository: ApplicationRepository,
    private val packageManagerSource: PackageManagerSource,
    private val sensitivePermissionInfo: SensitivePermissionInfo,
    private val sensitiveDataCategoryRepository: SensitiveDataCategoryRepository,
    private val dangerousGroupRepository: DangerousGroupRepository,
    private val newAppPermissionAnalysis: NewAppPermissionAnalysis
) {
    suspend fun initSystem() {

        //Llenar sensitiveDataCategory
        // Obtener todas las categorías únicas
        val categoriasUnicas = sensitivePermissionInfo.permissionsMap.values
            .map { it.category }
            .distinct()

        for (i in categoriasUnicas) {
            sensitiveDataCategoryRepository.insert(i)
        }

        sensitiveDataCategoryRepository.insert("no-category")

        dangerousGroupRepository.insert("no-group")

        //Llenar DangeroudGroupId

        val groupsCreated = mutableListOf<String>()

        val permissionList = permissionExtractInfoRepository.getPermissionList()

        for (i in permissionList) {

            val permissionGroup = packageManagerSource.getPermissionGroup(i.constant_permission_name)?: "no-group"

            if (permissionGroup !in groupsCreated) {
                dangerousGroupRepository.insert(permissionGroup)
                groupsCreated.add(permissionGroup)
            }

            val dangeroudGroupItem = dangerousGroupRepository.getOne(permissionGroup)

            val categoryName = sensitivePermissionInfo.getCategory(i.constant_permission_name)?: "no-category"

            val sensitiveDataCategoryItem = sensitiveDataCategoryRepository.getOne(categoryName)

            val permission = Permission(
                name = i.permission_name,
                constantName = i.constant_permission_name,
                protectionLevel = i.protection_level ?: "no-level",
                description = i.description,
                sensitiveDataCategoryId = sensitiveDataCategoryItem.uid,
                dangerousGroupId = dangeroudGroupItem.uid
            )
            permissionRepository.insertPermission(permission.toPermissionEntity())
        }
        //Inserta aplicaciones
        for (i in packageManagerSource.getInstalledApps()) {
            val app = Application(
                packageName = i[0],
                appName = i[1],
                createdAt = i[2],
                apkPath = i[3],
                uniqueUserId = i[4],
            )
            applicationRepository.insertApplication(app.toApplicationEntity())
        }

        val appList = applicationRepository.getAllApplications().map { it.toApplication() }

        for (i in appList) {
            val id = i.id
            //Add permission to application, tabla ApplicationPermission

            newAppPermissionAnalysis.invoke(i)

            //Tal vez consultar por Application Description y PrivacyPolicy

        }

        return
    }
}