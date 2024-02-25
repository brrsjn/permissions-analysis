package com.jbarros.permissionanalysis.ui.main.interaction

import com.jbarros.permissionanalysis.domain.model.Application

sealed class ApplicationEvent {
    object NavToHome: ApplicationEvent()
    object NotNavToHome: ApplicationEvent()
    data class AddApplication(val application: Application): ApplicationEvent()
    data class SelectApplication(val application: Application): ApplicationEvent()
    object SelectRiskAnalysis: ApplicationEvent()
    object ExtractApplicationList: ApplicationEvent()
    object GetDescriptions: ApplicationEvent()
    object GetPrivacyPolicies: ApplicationEvent()
    object SelectNewRiskAnalysis: ApplicationEvent()
    object SelectPermissionChange: ApplicationEvent()
    object SelectPermissionView: ApplicationEvent()
    object SelectDownloadReport: ApplicationEvent()
    object SelectNewRiskAnalysisToAllApps: ApplicationEvent()
}