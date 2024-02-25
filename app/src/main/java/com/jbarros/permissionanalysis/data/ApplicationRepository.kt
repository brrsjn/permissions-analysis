package com.jbarros.permissionanalysis.data

import com.jbarros.permissionanalysis.data.local.ApplicationDao
import com.jbarros.permissionanalysis.data.model.ApplicationEntity
import javax.inject.Inject

class ApplicationRepository @Inject constructor(
    private val applicationDao: ApplicationDao,
){
    fun getAllApplications(): List<ApplicationEntity> {
        return applicationDao.getAll()
    }

    fun getOne(applicationId: Int): ApplicationEntity {
        return applicationDao.getOne(applicationId)
    }

    fun insertApplication(applicationEntity: ApplicationEntity){
        applicationDao.insert(application = applicationEntity)
    }


    fun getApplicationIdByPackageName(packageName: String): ApplicationEntity {
        return applicationDao.getApplicationIdByPackageName(packageName = packageName)
    }
    fun update(applicationEntity: ApplicationEntity) {
        applicationDao.update(applicationEntity)
    }
}