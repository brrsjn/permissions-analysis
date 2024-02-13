package com.jbarros.permissionanalysis.domain.applications

import com.jbarros.permissionanalysis.data.ApplicationRepository
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.toApplication
import javax.inject.Inject

class GetApplications @Inject constructor(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(): List<Application> {
        return applicationRepository.getAllApplications().map {
            it.toApplication()
        }
    }
}