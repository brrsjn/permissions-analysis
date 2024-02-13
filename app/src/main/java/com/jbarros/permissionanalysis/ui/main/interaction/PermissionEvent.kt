package com.jbarros.permissionanalysis.ui.main.interaction

import com.jbarros.permissionanalysis.domain.model.Permission


sealed class PermissionEvent {
    data class SelectPermission(val permission: Permission): PermissionEvent()
}