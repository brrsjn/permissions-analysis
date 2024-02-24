package com.jbarros.permissionanalysis.ui.main.interaction

import com.jbarros.permissionanalysis.domain.model.*
import com.jbarros.permissionanalysis.domain.permission.GetPermissionsById
import com.jbarros.permissionanalysis.domain.riskAnalysis.GetSensitiveDataCategoryAndPermission

data class ApplicationState(
    var applications: List<Application> = emptyList(),
    var navToHome: Boolean = false,
    var selectedApplication: Application = Application(),
    var selectedPermissionAnalysis: PermissionAnalysis = PermissionAnalysis(),
    var selectedApplicationPermissions: List<ApplicationPermission> = emptyList(),
    var selectedSensitiveDataCategoryAndPermission: List<PermissionInfo> = emptyList(),
    var applicationDescriptions: Descriptions = Descriptions(),
    var applicationPrivacyPolicies: PrivacyPolicies = PrivacyPolicies(),
    var loadingApplicationDetailScreen: Boolean = false,
    var permissionChanges: List<PermissionChangeStrings> = emptyList(),
    var permissionsByApp: List<Permission> = emptyList(),
    var permissionsNameByApp: List<PermissionsName> = emptyList()

)
