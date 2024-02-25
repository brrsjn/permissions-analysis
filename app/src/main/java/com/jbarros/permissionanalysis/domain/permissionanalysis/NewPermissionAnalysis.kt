package com.jbarros.permissionanalysis.domain.permissionanalysis

import com.jbarros.permissionanalysis.data.ApplicationRepository
import com.jbarros.permissionanalysis.domain.model.Application

import com.jbarros.permissionanalysis.domain.model.toApplication
import com.jbarros.permissionanalysis.domain.model.toApplicationEntity
import com.jbarros.permissionanalysis.utils.DateProvider
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import javax.inject.Inject

class NewPermissionAnalysis @Inject constructor(
    private val applicationRepository: ApplicationRepository,
    private val packageManagerSource: PackageManagerSource,
    private val newAppPermissionAnalysis: NewAppPermissionAnalysis
) {
    suspend operator fun invoke() {
        val allInstalledApps = packageManagerSource.getInstalledApps()
        val allRegisteredApps = applicationRepository.getAllApplications()
        for(element in allInstalledApps) {
            val exist = allRegisteredApps.firstOrNull{it.packageName == element[0]}
            if (exist == null) {
                val app = Application(
                    packageName = element[0],
                    appName = element[1],
                    createdAt = element[2],
                    apkPath = element[3],
                    uniqueUserId = element[4],
                )
                applicationRepository.insertApplication(app.toApplicationEntity())
            }
        }
        for(app in allRegisteredApps) {
            val exist = allInstalledApps.firstOrNull{it[0] == app.packageName}

            if(exist == null) {
                val dateProvider = DateProvider()
                app.deletedAt = dateProvider.getDateTime()
                applicationRepository.update(app)
            }

        }
        for (i in applicationRepository.getAllApplications()) {
            newAppPermissionAnalysis.invoke(i.toApplication())
        }
        return
    }
}