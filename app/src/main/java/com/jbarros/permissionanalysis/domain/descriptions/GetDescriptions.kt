package com.jbarros.permissionanalysis.domain.descriptions

import com.jbarros.permissionanalysis.data.DescriptionsRepository
import com.jbarros.permissionanalysis.data.PermissionRepository
import com.jbarros.permissionanalysis.domain.model.Descriptions

import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.domain.model.toDescriptions
import com.jbarros.permissionanalysis.domain.model.toPermission
import javax.inject.Inject

class GetDescriptions @Inject constructor(private val descriptionsRepository: DescriptionsRepository) {
    suspend operator fun invoke(packageName: String): Descriptions {
        return descriptionsRepository.getDescriptions(packageName).toDescriptions()
    }
}