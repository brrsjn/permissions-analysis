package com.jbarros.permissionanalysis.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.domain.model.Application
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.theme.PermissionAnalysisTheme

@Composable
fun ApplicationCard(
    modifier: Modifier = Modifier,
    app: Application,
    onSelectedApp: (app: Application) -> Unit,
    onNavigate: (MainDestinations) -> Unit
) {
    MainDestinations.ApplicationDetail.route = "applicationDetail/" + app.id
    Card(
        modifier = modifier.clickable { onSelectedApp(app); onNavigate(MainDestinations.ApplicationDetail) },
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = app.appIcon.toBitmap().asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = app.appName,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = app.packageName, maxLines = 3, overflow = TextOverflow.Ellipsis)
                }
            }
        }

    }
}


@Composable
fun FakeApplicationCard() {
    PermissionAnalysisTheme() {
        ApplicationCard(
            app = Application(
                id = 1,
                appName = "Facebook",
                packageName = "facebook.org",
            ), onSelectedApp = {}, onNavigate = {}
        )
    }
}

@Composable
@Preview
fun ApplicationCardPreview() {
    FakeApplicationCard()
}