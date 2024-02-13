package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApplicationList(
    modifier: Modifier = Modifier,
    apps: List<Application>,
    onSelectedApp: (app: Application) -> Unit,
    onNavigate: (MainDestinations) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 175.dp), modifier = modifier.fillMaxSize()
    ) {

        items(items = apps) { app ->
                ApplicationCard(
                modifier = Modifier.padding(10.dp), app = app, onSelectedApp = onSelectedApp, onNavigate = onNavigate
            )
        }


    }
}

@Composable
fun FakeApplicationList() {
    PermissionAnalysisTheme() {
        val apps = listOf(Application(
            id = 1,
            appName = "Facebook",
            packageName = "facebook.org",
        ),Application(
            id = 2,
            appName = "Facebook",
            packageName = "facebook.org",
        ),Application(
            id = 3,
            appName = "Facebook",
            packageName = "facebook.org",
        ),Application(
            id = 4,
            appName = "Facebook",
            packageName = "facebook.org",
        ))
        ApplicationList(apps = apps, onSelectedApp = {}, onNavigate = {})
    }
}
@Composable
@Preview
fun ApplicationListPreview() {
    FakeApplicationList()
}