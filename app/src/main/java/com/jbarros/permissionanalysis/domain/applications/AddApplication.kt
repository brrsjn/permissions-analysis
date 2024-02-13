package com.jbarros.permissionanalysis.domain.applications

import com.jbarros.permissionanalysis.data.ApplicationRepository
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.toApplicationEntity
import javax.inject.Inject

class AddApplication @Inject constructor(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(application: Application) {
        applicationRepository.insertApplication(application.toApplicationEntity())
    }
}