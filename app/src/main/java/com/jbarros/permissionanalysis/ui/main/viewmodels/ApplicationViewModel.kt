package com.jbarros.permissionanalysis.ui.main.viewmodels

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jbarros.permissionanalysis.domain.applicationpermission.GetApplicationPermissionByApp
import com.jbarros.permissionanalysis.domain.applications.GetApplications
import com.jbarros.permissionanalysis.domain.descriptions.GetDescriptions
import com.jbarros.permissionanalysis.domain.exportData.ExportFileData
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.domain.model.ExportData
import com.jbarros.permissionanalysis.domain.model.PermissionChangeStrings
import com.jbarros.permissionanalysis.domain.model.PermissionsName
import com.jbarros.permissionanalysis.domain.permission.GetPermissionsById
import com.jbarros.permissionanalysis.domain.permission.GetPermissionsNameById
import com.jbarros.permissionanalysis.domain.permissionChange.GetPermissionChanges
import com.jbarros.permissionanalysis.domain.permissionanalysis.GetPermissionAnalysis
import com.jbarros.permissionanalysis.domain.permissionanalysis.NewAppPermissionAnalysis
import com.jbarros.permissionanalysis.domain.permissionanalysis.NewPermissionAnalysis
import com.jbarros.permissionanalysis.domain.privacyPolicies.GetPrivacyPolicies
import com.jbarros.permissionanalysis.domain.riskAnalysis.GetSensitiveDataCategoryAndPermission
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationEvent
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val getApplications: GetApplications,
    private val packageManagerSource: PackageManagerSource,
    private val getPermissionAnalysis: GetPermissionAnalysis,
    private val getApplicationPermissionByApp: GetApplicationPermissionByApp,
    private val getSensitiveDataCategoryAndPermission: GetSensitiveDataCategoryAndPermission,
    private val getDescriptions: GetDescriptions,
    private val getPrivacyPolicies: GetPrivacyPolicies,
    private val newAppPermissionAnalysis: NewAppPermissionAnalysis,
    private val getPermissionChanges: GetPermissionChanges,
    private val getPermissionsById: GetPermissionsById,
    private val getPermissionsNameById: GetPermissionsNameById,
    private val exportFileData: ExportFileData,
    @ApplicationContext private val context: Context,
    private val newPermissionAnalysis: NewPermissionAnalysis
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
                onSelectPermissions(applicationId = applicationEvent.application.id)
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
            is ApplicationEvent.GetDescriptions -> {
                onSelectDescriptions()
            }
            is ApplicationEvent.GetPrivacyPolicies -> {
                onSelectPrivacyPolicies()
            }
            is ApplicationEvent.SelectNewRiskAnalysis -> {
                onSelectNewRiskAnalysis()
            }
            is ApplicationEvent.SelectPermissionChange -> {
                onSelectPermissionChange()
            }
            is ApplicationEvent.SelectPermissionView -> {
                onSelectPermissions(state.value.selectedApplication.id)
            }
            is ApplicationEvent.SelectDownloadReport -> {
                saveAndShareJson()
            }
            is ApplicationEvent.SelectNewRiskAnalysisToAllApps -> {
                onSelectNewRiskAnalysisToAllApps()
            }

        }
    }

    private fun onSelectRiskAnalysis() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "onSelectRiskAnalysis: Entrando al metodo")
            var fetchedApplication =
                getSensitiveDataCategoryAndPermission.invoke(_state.value.selectedApplication.id)
            Log.d(TAG, "onSelectRiskAnalysis: Entrando al metodo")
            withContext(Dispatchers.Main) {
                _state.value =
                    _state.value.copy(selectedSensitiveDataCategoryAndPermission = fetchedApplication)
            }
        }
    }

    private fun collectApplications() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            var fetchedApplication = getApplications.invoke()
            fetchedApplication.map {
                it.appIcon = packageManagerSource.getAppDrawable(it.packageName)
            }
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
            for (app in _state.value.applications) {
                //newAppPermissionAnalysis(app)
            }
        }
    }

    private fun onSelectApplication(application: Application) {
        _state.value = _state.value.copy(
            loadingApplicationDetailScreen = false,
            selectedApplication = application
        )
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            val applicationPermissionAnalysis = getPermissionAnalysis(application.id)
            val applicationPermission = getApplicationPermissionByApp(application.id)

            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    selectedPermissionAnalysis = applicationPermissionAnalysis,
                    selectedApplicationPermissions = applicationPermission,
                    loadingApplicationDetailScreen = true
                )
            }
        }
    }

    private fun collectInstalledApplication() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            //getInstalledApplications()
            collectApplications()
        }
    }

    private fun onSelectDescriptions() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            val descriptions = getDescriptions(state.value.selectedApplication.packageName)
            withContext(Dispatchers.Main) {
                _state.value =
                    _state.value.copy(applicationDescriptions = descriptions)
            }
        }
    }

    private fun onSelectPrivacyPolicies() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            val privacyPolicies = getPrivacyPolicies(state.value.selectedApplication.packageName)
            withContext(Dispatchers.Main) {
                _state.value =
                    _state.value.copy(applicationPrivacyPolicies = privacyPolicies)
            }
        }
    }

    private fun onSelectNewRiskAnalysis() {
        viewModelScope.launch(Dispatchers.IO) {
            newAppPermissionAnalysis.invoke(_state.value.selectedApplication)
            //Logica de nuevo analizis
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Nuevo Analisis completado.", Toast.LENGTH_LONG).show()
                //Actualizacion a la vista
            }
        }
    }

    private fun onSelectPermissionChange() {
        viewModelScope.launch(Dispatchers.IO) {
            //Logica de nuevo analizis
            val permissionChanges = getPermissionChanges.invoke(_state.value.selectedApplication.id)

            withContext(Dispatchers.Main) {
                //Actualizacion a la vista
                _state.value =
                    _state.value.copy(permissionChanges = permissionChanges)
            }
        }
    }

    private fun onSelectPermissions(applicationId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val permissions = getPermissionsNameById.invoke(applicationId)
            withContext(Dispatchers.Main) {

                //Actualizacion a la vista
                _state.value =
                    _state.value.copy(permissionsNameByApp = permissions)
            }
        }
    }

    private fun saveAndShareJson() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = exportFileData.invoke(applicationId = state.value.selectedApplication.id)
            val file = saveDataToFile(data)
            withContext(Dispatchers.Main) {
                shareJsonFile(file)
                Toast.makeText(context, "JSON file saved.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDataToFile(dataList: ExportData): File {
        val gson = Gson()
        val jsonData = gson.toJson(dataList)
        // Obtén la fecha actual en el formato deseado para el nombre del archivo
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val packageName = state.value.selectedApplication.packageName.replace('.', '_')

        // Combina la fecha con el nombre del archivo
        val fileName = "${packageName}_$timeStamp.json"
        val folder: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, fileName)

        try {
            FileWriter(file).use { writer ->
                writer.write(jsonData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return file
    }

    private fun shareJsonFile(file: File) {
        val fileUri: Uri = FileProvider.getUriForFile(
            context,
            "com.jbarros.permissionanalysis.fileprovider",
            file
        )

        val shareIntent = getShareIntent(fileUri)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this flag
        context.startActivity(shareIntent)
    }

    private fun getShareIntent(fileUri: Uri): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.type = "application/json"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return Intent.createChooser(shareIntent, "Compartir archivo JSON")
    }

    private fun onSelectNewRiskAnalysisToAllApps() {
        viewModelScope.launch(Dispatchers.IO) {
            newPermissionAnalysis.invoke()
            collectApplications()
            withContext(Dispatchers.Main) {

                Toast.makeText(context, "Actualizado el listado de apps y análisis de riesgo.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
