package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme

@Composable
fun CardTest() {
    Card() {
        Column() {
            Text("Hola Card")
            Text("Hola Card 2")
        }

    }
}

@Composable
@Preview
fun FakeCardTest() {
    PermissionAnalysisTheme() {
        CardTest()
    }
}