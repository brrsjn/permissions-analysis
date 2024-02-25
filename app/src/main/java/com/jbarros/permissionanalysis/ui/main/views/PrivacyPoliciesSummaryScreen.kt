package com.jbarros.permissionanalysis.ui.main.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toBitmap
import com.jbarros.permissionanalysis.ui.main.MainDestinations
import com.jbarros.permissionanalysis.ui.main.interaction.ApplicationState

@Composable
fun PrivacyPoliciesSummaryScreen(
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
                text = "Resumen de politica\n" +
                        " de privacidad "
            )
            val privacyPolicies = applicationState.applicationPrivacyPolicies

            if (privacyPolicies.link != "") {
                //OpenLinkInBrowser(privacyPolicies.link, "Abrir Politica de privacidad")
                CustomAlertDialogGoToPrivacyPolicy(link = privacyPolicies.link)
            }


            PrivacyPolicyItem("Policy Introductory", privacyPolicies.policy_introductory)
            PrivacyPolicyItem(
                "First Party Collection and Use",
                privacyPolicies.first_party_collection_and_use
            )
            PrivacyPolicyItem(
                "Cookies and Similar Technologies",
                privacyPolicies.cookies_and_similar_technologies
            )
            PrivacyPolicyItem("Data Retention", privacyPolicies.data_retention)
            PrivacyPolicyItem(
                "Third Party Share and Collection",
                privacyPolicies.third_party_share_and_collection
            )
            PrivacyPolicyItem("Data Security", privacyPolicies.data_security)
            PrivacyPolicyItem(
                "International Data Transfer",
                privacyPolicies.international_data_transfer
            )
            PrivacyPolicyItem(
                "User Right and Control",
                privacyPolicies.user_right_and_control
            )
            PrivacyPolicyItem("Specific Audiences", privacyPolicies.specific_audiences)
            PrivacyPolicyItem("Policy Change", privacyPolicies.policy_change)
            PrivacyPolicyItem(
                "Policy Contact Information",
                privacyPolicies.policy_contact_information
            )
            // Agrega más elementos según sea necesario


        }


    }
}

@Composable
fun OpenLinkInBrowser(link: String, text: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            // Utilizar un Intent para abrir el enlace en el navegador
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(context, intent, null)
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Composable
fun PrivacyPolicyItem(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
        Text(text = content, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun CustomAlertDialogGoToPrivacyPolicy(
    link: String
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Handle dismiss action
                showDialog = false
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = "Saldrás de la aplicación")
                }
            },
            text = {
                Text(text = "Saldrás de la aplicación e irás a pagina web del fabricante.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        // Utilizar un Intent para abrir el enlace en el navegador
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link)
                        )
                        ContextCompat.startActivity(context, intent, null)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Ir")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Handle dismiss action
                        showDialog = false
                    }
                ) {
                    Text("Quedarme")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    // The button to trigger the dialog
    Button(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        onClick = {
            showDialog = true
        }
    ) {
        Text("Abrir Politica de privacidad")
    }
}

