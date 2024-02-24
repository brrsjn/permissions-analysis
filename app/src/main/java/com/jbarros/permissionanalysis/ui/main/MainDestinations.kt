package com.jbarros.permissionanalysis.ui.main

sealed class MainDestinations(var route: String) {
    object HomeScreen: MainDestinations(route = "home")
    object ApplicationDetail: MainDestinations(route = "applicationDetail/{applicationId}")
    object AppliedTechnique: MainDestinations(route = "appliedTechnique")
    object DescriptionAnalysis: MainDestinations(route = "descriptionAnalysis")
    object PrivacyPoliciesSummary: MainDestinations(route = "privacyPoliciesSummary")
    object RiskAnalysis: MainDestinations(route = "riskAnalysis")
    object PermissionDetail: MainDestinations(route = "permissionDetail/{permissionId}")
    object PermissionsList: MainDestinations(route = "permissionsList")
    object PermissionsChange: MainDestinations(route = "permissionsChange")
}
