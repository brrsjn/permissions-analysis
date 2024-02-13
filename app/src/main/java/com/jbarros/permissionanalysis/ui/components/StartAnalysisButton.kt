package com.jbarros.permissionanalysis.ui.components

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationEvent
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme

@Composable
fun StartAnalysisButton(
    modifier: Modifier,
    onSelectedButton: (ApplicationEvent) -> Unit
) {
    Button(colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = Color.Red), onClick = { onSelectedButton(ApplicationEvent.ExtractApplicationList)}) {
        Text("Análisis")
    }

}

fun sendlog() {
    Log.d("APPLICATION", "Analisis seleccionado")
}

@Composable
fun FakeStartAnalysisButton() {
    PermissionAnalysisTheme() {
        StartAnalysisButton(modifier = Modifier, onSelectedButton = {})
    }
}

@Composable
@Preview
fun StartAnalysisButtonPreview() {
    FakeStartAnalysisButton()
}