package com.jbarros.permissionanalysis.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.jbarros.permissionanalysis.domain.permissionanalysis.NewAppPermissionAnalysisByPackageName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class PackageChangeReceiver @Inject constructor(
    private val newAppPermissionAnalysisByPackageName: NewAppPermissionAnalysisByPackageName
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        val packageName = intent?.dataString?.substring("package:".length)
        Log.d("ONRECEIVE", "PArece que entró")
        when (intent?.action ) {
            Intent.ACTION_PACKAGE_ADDED-> {
                // Llama al método de tu capa de dominio
                packageName?.let {
                    GlobalScope.launch(Dispatchers.Default) {
                        // Ejecuta tu lógica de dominio en una corutina
                        newAppPermissionAnalysisByPackageName.invoke(packageName)
                    }
                }
            }
            Intent.ACTION_PACKAGE_CHANGED-> {
                // Llama al método de tu capa de dominio
                packageName?.let {
                    GlobalScope.launch(Dispatchers.Default) {
                        // Ejecuta tu lógica de dominio en una corutina
                        newAppPermissionAnalysisByPackageName.invoke(packageName)
                    }
                }
            }
        }
    }
}