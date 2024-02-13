package com.jbarros.permissionanalysis.ui.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.ui.components.ApplicationList
import com.jbarros.permissionanalysis.ui.components.StartAnalysisButton
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationEvent
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme

@Composable
fun HomeScreen(
    onNavigate: (MainDestinations) -> Unit,
    applicationState: ApplicationState,
    onSelectedApp: (Application) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                title = { Text("Permission Analysis") },
            )
        }
    ) {
        val totalApplications = applicationState.applications.size
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = "Resultados del análisis ",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 50.dp)
            )
            Text(
                text = "$totalApplications aplicaciones analizadas",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            ApplicationList(
                modifier = Modifier.padding(it),
                apps = applicationState.applications,
                onSelectedApp = onSelectedApp, onNavigate = onNavigate
            )
        }
    }
}

@Composable
fun FakeHomeScreen() {
    PermissionAnalysisTheme {
        HomeScreen(
            onNavigate = {},
            applicationState = ApplicationState(),
            onSelectedApp = {})
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    FakeHomeScreen()
}