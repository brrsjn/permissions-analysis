package com.jbarros.permissionanalysis.ui.main.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbarros.permissionanalysis.domain.applicationpermission.GetApplicationPermissionByApp
import com.jbarros.permissionanalysis.domain.applications.GetApplications
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.permissionanalysis.GetPermissionAnalysis
import com.jbarros.permissionanalysis.domain.riskAnalysis.GetSensitiveDataCategoryAndPermission
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationEvent
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val getApplications: GetApplications,
    private val packageManagerSource: PackageManagerSource,
    private val getPermissionAnalysis: GetPermissionAnalysis,
    private val getApplicationPermissionByApp: GetApplicationPermissionByApp,
    private val getSensitiveDataCategoryAndPermission: GetSensitiveDataCategoryAndPermission
) : ViewModel() {
    private val _state: MutableState<ApplicationState> = mutableStateOf(ApplicationState())
    val state: State<ApplicationState> get() = _state

    init {
        collectApplications()
    }

    fun onEvent(applicationEvent: ApplicationEvent) {
        when (applicationEvent) {
            is ApplicationEvent.AddApplication -> {
                onInsertApplication(application = applicationEvent.application)
                setNavToHome()
            }

            ApplicationEvent.NavToHome -> {
                setNavToHome()
            }
            ApplicationEvent.NotNavToHome -> {
                unSetNavToHome()
            }
            is ApplicationEvent.SelectApplication -> {
                onSelectApplication(application = applicationEvent.application)
            }
            is ApplicationEvent.ExtractApplicationList -> {
                Log.d("APPLICATION", "entró al extract app list")
                //collectInstalledApplication()
                newAnalysis()
                collectApplications()

            }
            is ApplicationEvent.SelectRiskAnalysis -> {
                onSelectRiskAnalysis()
            }
        }
    }

    private fun onSelectRiskAnalysis() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "onSelectRiskAnalysis: Entrando al metodo")
            var fetchedApplication = getSensitiveDataCategoryAndPermission.invoke(_state.value.selectedApplication.id)
            Log.d(TAG, "onSelectRiskAnalysis: Entrando al metodo")
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(selectedSensitiveDataCategoryAndPermission = fetchedApplication)
            }
        }
    }

    private fun collectApplications() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            var fetchedApplication = getApplications.invoke()
            fetchedApplication.map { it.appIcon = packageManagerSource.getAppDrawable(it.packageName) }
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(applications = fetchedApplication)
            }
        }
    }

    private fun onInsertApplication(application: Application) {
        viewModelScope.launch(Dispatchers.IO) {
            //addApplication(application = application)
            //collectApplications()
        }
    }

    private fun setNavToHome() {
        _state.value = _state.value.copy(navToHome = true)
    }

    private fun unSetNavToHome() {
        _state.value = _state.value.copy(navToHome = false)
    }

    private fun newAnalysis() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            for(app in _state.value.applications) {
                //newAppPermissionAnalysis(app)
            }
        }
    }

    private fun onSelectApplication(application: Application) {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            val applicationPermissionAnalysis = getPermissionAnalysis(application.id)
            val applicationPermission = getApplicationPermissionByApp(application.id)
            val filePath: String = application.apkPath
            Log.d("APPLICATION", "Aplicacion seleccionada $filePath")            // Volver al hilo principal
            _state.value = _state.value.copy(selectedApplication = application)
            _state.value = _state.value.copy(selectedPermissionAnalysis = applicationPermissionAnalysis)
            _state.value = _state.value.copy(selectedApplicationPermissions = applicationPermission)
        }
    }

    private fun collectInstalledApplication() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            //getInstalledApplications()
            collectApplications()
        }
    }
}
