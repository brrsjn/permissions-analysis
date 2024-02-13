package com.jbarros.permissionanalysis.ui.main.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbarros.permissionanalysis.domain.model.Permission
import com.jbarros.permissionanalysis.ui.main.interaction.PermissionEvent
import com.jbarros.permissionanalysis.ui.main.interaction.PermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
// Función para generar datos ficticios
fun generateDummyPermissions(count: Int): List<Permission> {
    val dummyPermissions = mutableListOf<Permission>()

    for (i in 1..count) {
        dummyPermissions.add(Permission(id = i, name = "Permission $i"))
    }

    return dummyPermissions
}
@HiltViewModel
class PermissionViewModel @Inject constructor(
) : ViewModel() {
    private val _state: MutableState<PermissionState> = mutableStateOf(PermissionState())
    val state: State<PermissionState> get() = _state

    init {
        collectPermissions()
    }

    fun onEvent(applicationEvent: PermissionEvent) {
        when (applicationEvent) {
            is PermissionEvent.SelectPermission -> {
                onSelectPermission(permission = applicationEvent.permission)
            }
        }
    }

    private fun collectPermissions() {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedPermission: List<Permission> = generateDummyPermissions(10)
            // Volver al hilo principal
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(permissions = fetchedPermission)
            }
        }
    }

    private fun onSelectPermission(permission: Permission) {
        // Iniciar hilo secundario
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(selectedPermission = permission)
        }
    }
}
