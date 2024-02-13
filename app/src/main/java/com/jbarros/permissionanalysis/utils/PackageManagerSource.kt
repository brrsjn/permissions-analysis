package com.jbarros.permissionanalysis.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PackageManagerSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    public fun getInstalledApps(): MutableList<MutableList<String>> {
        val dateProvider = DateProvider()
        val pm = context.packageManager
        // Obtener una lista de aplicaciones instaladas.
        val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val appList: MutableList<MutableList<String>> = mutableListOf()

        for (packageInfo in packages) {
            // Verificar si la aplicación no es del sistema
            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 && !packageInfo.packageName.contains(
                    "samsung"
                )
            ) {
                val packageName = packageInfo.packageName
                val applicationInfo = pm.getApplicationInfo(packageName, 0)
                val uid = applicationInfo.uid.toString()
                val appName = packageInfo.applicationInfo.loadLabel(pm).toString()

                val apkPath = packageInfo.applicationInfo.sourceDir
                val appPerms = packageInfo.requestedPermissions?.toList() ?: emptyList()
                val grantedPermissions = mutableListOf<String>()

                appPerms?.forEach { permission ->
                    if (pm.checkPermission(permission, packageName) == PackageManager.PERMISSION_GRANTED) {
                        grantedPermissions.add(permission)
                    }
                }
                val tmpApplication: MutableList<String> = mutableListOf(packageInfo.packageName, appName, dateProvider.getDateTime(), apkPath, uid)
                appList.add(tmpApplication)
            }
        }

        Log.d("APPLICATION-PACKAGE", "Bajando los permisos")
        return appList

    }
    fun getAppDrawable(packageName: String): Drawable {
        val pm = context.packageManager
        val applicationInfo = pm.getApplicationInfo(packageName, 0)

        return pm.getApplicationIcon(applicationInfo)
    }

    fun getGrantedPermissions(packageName: String): MutableList<String> {
        val pm = context.packageManager
        val appPerms = getRequestedPermissions(packageName)
        val grantedPermissions = mutableListOf<String>()


        appPerms?.forEach { permission ->
            if (pm.checkPermission(permission, packageName) == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permission)
            }
        }

        return grantedPermissions
    }

    fun getRequestedPermissions(packageName: String): List<String> {
        val pm = context.packageManager
        val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        return packageInfo.requestedPermissions?.toList() ?: emptyList()
    }

    fun getPermissionGroup(permission: String): String? {
        val packageManager = context.packageManager

        try {
            val permissionInfo = packageManager.getPermissionInfo(permission, 0)

            // Puedes acceder al grupo de permisos desde la propiedad group de PermissionInfo
            val permissionGroup = permissionInfo.group

            return permissionGroup
        } catch (e: PackageManager.NameNotFoundException) {
            // Manejar la excepción si el permiso no se encuentra
            e.printStackTrace()
        }

        return null
    }

}

