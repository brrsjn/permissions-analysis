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

    fun insertApplication(applicationEntity: ApplicationEntity){
        applicationDao.insert(application = applicationEntity)
    }


    fun getApplicationIdByPackageName(packageName: String): Int {
        return applicationDao.getApplicationIdByPackageName(packageName = packageName)
    }
}