package com.jbarros.permissionanalysis.ui.main.interaction

import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.domain.model.PermissionAnalysis

data class ApplicationState(
    var applications: List<Application> = emptyList(),
    var navToHome: Boolean = false,
    var selectedApplication: Application = Application(),
    var selectedPermissionAnalysis: PermissionAnalysis = PermissionAnalysis(),
    var selectedApplicationPermissions: List<ApplicationPermission> = emptyList(),
    )
