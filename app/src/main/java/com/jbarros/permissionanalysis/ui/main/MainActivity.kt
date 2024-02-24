package com.jbarros.permissionanalysis.ui.main


import android.content.Context

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jbarros.permissionanalysis.domain.initializer.FirstRun
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationEvent
import com.jbarros.permissionanalysis.ui.main.interaction.PermissionEvent
import com.jbarros.permissionanalysis.ui.main.viewmodels.ApplicationViewModel
import com.jbarros.permissionanalysis.ui.main.viewmodels.PermissionViewModel
import com.jbarros.permissionanalysis.ui.main.views.*
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var initializer: FirstRun

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // Mostrar una pantalla de carga aquí
            setContent {
                LoadingScreen() // Un Composable que representa una pantalla de carga
            }

            // Iniciar una coroutine para realizar operaciones en segundo plano
            lifecycleScope.launch(Dispatchers.IO) {
                initializer.initSystem()

                // Después de completar la inicialización, actualizamos la preferencia
                prefs.edit().putBoolean("isFirstRun", false).apply()

                // Después de la inicialización, establecer el contenido normal de tu aplicación
                setContent {
                    PermissionAnalysisTheme {
                        content()
                    }
                }
            }
        } else {
            setContent {
                PermissionAnalysisTheme {
                    // Tu navegación y UI van aquí
                    content()
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    // Un simple Composable que muestra una pantalla de carga
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator() // Muestra un spinner de carga
    }
}

@Composable
private fun content() {
    val navController = rememberNavController()
    val applicationViewModel: ApplicationViewModel = hiltViewModel()
    val permissionViewModel: PermissionViewModel = hiltViewModel()
    NavHost(
        navController = navController, startDestination = MainDestinations.HomeScreen.route
    ) {
        composable(MainDestinations.HomeScreen.route) {
            HomeScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            },
                applicationState = applicationViewModel.state.value,
                onSelectedApp = { application ->
                    Log.d("APPLICATION", "En main activity")
                    applicationViewModel.onEvent(
                        ApplicationEvent.SelectApplication(
                            application = application
                        )
                    )
                })
        }
        composable(MainDestinations.ApplicationDetail.route) {
            val state = applicationViewModel.state.value
            ApplicationDetailScreen(
                onNavigate = { screen ->
                    navigate(navHostController = navController, destination = screen)
                }, applicationState = state, onSelectNewRiskAnalysis = {
                    Log.d("APPLICATION", "En main activity")
                    applicationViewModel.onEvent(
                        ApplicationEvent.SelectNewRiskAnalysis
                    )
                }, onSelectPermissionChange = {
                    applicationViewModel.onEvent(
                        ApplicationEvent.SelectPermissionChange
                    )
                },onSelectPermissionView = {
                    applicationViewModel.onEvent(
                        ApplicationEvent.SelectPermissionView
                    )
                }
            )
        }
        composable(MainDestinations.PermissionsList.route) {
            val state = permissionViewModel.state.value
            PermissionListScreen(
                permissionState = state,
                onSelectedPermission = { permission ->
                    Log.d("APPLICATION", "En main activity")
                    permissionViewModel.onEvent(
                        PermissionEvent.SelectPermission(permission)
                    )
                },
                onNavigate = { screen ->
                    navigate(navHostController = navController, destination = screen)
                },
            )
        }
        composable(MainDestinations.PermissionDetail.route) {
            val state = applicationViewModel.state.value
            PermissionDetailScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            }, applicationState = state)
        }
        composable(MainDestinations.AppliedTechnique.route) {
            val state = applicationViewModel.state.value
            AppliedTechniqueScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            }, applicationState = state, onRiskAnalysisSelect = {
                applicationViewModel.onEvent(
                    ApplicationEvent.SelectRiskAnalysis
                )
            }, onDescriptionsSelect = {
                applicationViewModel.onEvent(
                    ApplicationEvent.GetDescriptions
                )
            }, onPrivacyPoliciesSelect = {
                applicationViewModel.onEvent(
                    ApplicationEvent.GetPrivacyPolicies
                )
            })
        }
        composable(MainDestinations.DescriptionAnalysis.route) {
            val state = applicationViewModel.state.value
            DescriptionAnalysisScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            }, applicationState = state)
        }
        composable(MainDestinations.PrivacyPoliciesSummary.route) {
            val state = applicationViewModel.state.value
            PrivacyPoliciesSummaryScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            }, applicationState = state)
        }
        composable(MainDestinations.RiskAnalysis.route) {
            val state = applicationViewModel.state.value
            RiskAnalysisScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            }, applicationState = state)
        }
        composable(MainDestinations.PermissionsChange.route) {
            val state = applicationViewModel.state.value
            PermissionChangeScreen(onNavigate = { screen ->
                navigate(navHostController = navController, destination = screen)
            }, applicationState = state)
        }
    }
}

private fun navigate(navHostController: NavHostController, destination: MainDestinations) {
    navHostController.navigate(destination.route) {
        // Guarda el estado de una pantalla
        popUpTo(navHostController.graph.findStartDestination().id) {
            saveState = true
        }
        // Una pantalla solo se ejecuta una vez o mantener una sola instancia
        launchSingleTop = true
    }
}
