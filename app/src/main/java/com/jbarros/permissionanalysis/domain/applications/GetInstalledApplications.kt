package com.jbarros.permissionanalysis.domain.applications

import com.jbarros.permissionanalysis.data.ApplicationRepository
import javax.inject.Inject

class GetInstalledApplications @Inject constructor(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke() {
        applicationRepository.getAllApplications()
    }
}