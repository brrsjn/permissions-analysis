package com.jbarros.permissionanalysis.ui.main.interaction

import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.domain.model.PermissionAnalysis
import com.jbarros.permissionanalysis.domain.model.PermissionInfo
import com.jbarros.permissionanalysis.domain.riskAnalysis.GetSensitiveDataCategoryAndPermission

data class ApplicationState(
    var applications: List<Application> = emptyList(),
    var navToHome: Boolean = false,
    var selectedApplication: Application = Application(),
    var selectedPermissionAnalysis: PermissionAnalysis = PermissionAnalysis(),
    var selectedApplicationPermissions: List<ApplicationPermission> = emptyList(),
    var selectedSensitiveDataCategoryAndPermission: List<PermissionInfo> = emptyList()
    )
