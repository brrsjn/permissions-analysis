package com.jbarros.permissionanalysis.domain.privacyPolicies

import com.jbarros.permissionanalysis.data.PrivacyPoliciesRepository
import com.jbarros.permissionanalysis.domain.model.PrivacyPolicies
import com.jbarros.permissionanalysis.domain.model.toPrivacyPolicies
import javax.inject.Inject

class GetPrivacyPolicies @Inject constructor(private val privacyPoliciesRepository: PrivacyPoliciesRepository) {
    suspend operator fun invoke(packageName: String): PrivacyPolicies {
        return privacyPoliciesRepository.getPrivacyPolicies(packageName).toPrivacyPolicies()
    }
}