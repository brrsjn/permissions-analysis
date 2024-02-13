package com.jbarros.permissionanalysis.ui.main

sealed class MainDestinations(var route: String) {
    object HomeScreen: MainDestinations(route = "home")
    object ApplicationDetail: MainDestinations(route = "applicationDetail/{applicationId}")
    object AppliedTechnique: MainDestinations(route = "appliedTechnique")
    object PermissionDetail: MainDestinations(route = "permissionDetail/{permissionId}")
    object PermissionsList: MainDestinations(route = "permissionsList")
}
