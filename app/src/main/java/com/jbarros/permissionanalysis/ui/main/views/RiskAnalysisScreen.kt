package com.jbarros.permissionanalysis.ui.main.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.domain.model.ApplicationPermission
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState

@Composable
fun RiskAnalysisScreen(
    onNavigate: (MainDestinations) -> Unit,
    applicationState: ApplicationState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permission Analysis") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(MainDestinations.HomeScreen) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()), // Añadir el desplazamiento vertical
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = applicationState.selectedApplication.appIcon.toBitmap()
                        .asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 10.dp)
                )
                Column(
                    modifier = Modifier.weight(1f) // Esto permite que el texto ocupe todo el espacio disponible
                ) {
                    Text(
                        text = applicationState.selectedApplication.appName,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = applicationState.selectedApplication.packageName,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Text(
                text = "Categorías de permisos sensibles\n" +
                        " concedidos "
            )

            var permisoPeligroso = mutableListOf<ApplicationPermission>()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                //.background(Color.Gray)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Primera columna con la mitad del ancho
                Box(
                    modifier = Modifier
                        //.background(Color.Blue)
                        .weight(1f)
                ) {
                    // Contenido de la primera columna
                    Text("Categoria Sensible", modifier = Modifier.padding(16.dp))
                }

                // Segunda columna con la mitad del ancho
                Box(
                    modifier = Modifier
                        //.background(Color.Green)
                        .weight(1f)
                ) {
                    // Contenido de la segunda columna
                    Text("Permisos", modifier = Modifier.padding(16.dp))
                }
            }
            for (i in applicationState.selectedSensitiveDataCategoryAndPermission) {
                if (i.sensitiveDataCategoryName != "no-category") {
                    //Text(text = i.permissionName +" - "+ i.sensitiveDataCategoryName)
                    // Row con dos columnas, cada una ocupando la mitad de la pantalla
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                        //.background(Color.Gray)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Primera columna con la mitad del ancho
                        Box(
                            modifier = Modifier
                                //.background(Color.Blue)
                                .weight(1f)
                        ) {
                            // Contenido de la primera columna
                            Text(i.sensitiveDataCategoryName, modifier = Modifier.padding(16.dp))
                        }

                        // Segunda columna con la mitad del ancho
                        Box(
                            modifier = Modifier
                                //.background(Color.Green)
                                .weight(1f)
                        ) {
                            // Contenido de la segunda columna
                            Text(i.permissionName, modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Riesgo total")
                    if (applicationState.selectedPermissionAnalysis.riskScoreRequested == 0) {
                        Text(text = "No riesgo")
                    }
                    if (applicationState.selectedPermissionAnalysis.riskScoreRequested == 1) {
                        NumberInCircle(1, Color.Green, Color.White)
                        Text(text = "Muy bajo")
                    }
                    if (applicationState.selectedPermissionAnalysis.riskScoreRequested == 2) {
                        NumberInCircle(2, Color.Cyan, Color.White)
                        Text(text = "Bajo")
                    }
                    if (applicationState.selectedPermissionAnalysis.riskScoreRequested == 3) {
                        NumberInCircle(3, Color.Yellow, Color.White)
                        Text(text = "Medio")
                    }
                    if (applicationState.selectedPermissionAnalysis.riskScoreRequested == 4) {
                        NumberInCircle(4, Color.Magenta, Color.White)
                        Text(text = "Alto")
                    }
                    if (applicationState.selectedPermissionAnalysis.riskScoreRequested == 5) {
                        NumberInCircle(5, Color.Red, Color.White)
                        Text(text = "Muy alto")                    }
                }
            }

        }
    }
}

@Composable
fun NumberInCircle(
    number: Int,
    circleColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(circleColor, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = textColor,
            style = MaterialTheme.typography.body1
        )
    }
}