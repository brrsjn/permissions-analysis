package com.jbarros.permissionanalysis.ui.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationEvent
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme

@Composable
fun ViewPermissionButton(
    modifier: Modifier,
    onSelectedButton: (ApplicationEvent) -> Unit
) {
    Button(onClick = { onSelectedButton(ApplicationEvent.ExtractApplicationList)}) {
        Text("Análisis")
    }

}

@Composable
fun FakeViewPermissionButton() {
    PermissionAnalysisTheme() {
        ViewPermissionButton(modifier = Modifier, onSelectedButton = {})
    }
}

@Composable
@Preview
fun ViewPermissionButtonPreview() {
    FakeViewPermissionButton()
}