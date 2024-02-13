package com.jbarros.permissionanalysis.ui.main.interaction

import com.jbarros.permissionanalysis.domain.model.Permission

data class PermissionState(
    var permissions: List<Permission> = emptyList(),
    var navToHome: Boolean = false,
    var selectedPermission: Permission = Permission()
)