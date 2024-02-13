package com.jbarros.permissionanalysis.domain.model

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.jbarros.permissionanalysis.data.model.ApplicationEntity


data class Application(
    val id: Int = 0,
    val packageName: String = "",
    val appName: String = "",
    val createdAt: String = "",
    val apkPath: String = "",
    var appIcon: Drawable = ColorDrawable(Color.RED),
    val uniqueUserId: String = "",
)


fun Application.toApplicationEntity(): ApplicationEntity = ApplicationEntity(
    uid = id,
    packageName = packageName,
    appName = appName,
    uniqueUserId = uniqueUserId,
    createdAt = createdAt,
    apkPath = apkPath,
)

fun ApplicationEntity.toApplication(): Application = Application(
    id = uid,
    packageName = packageName,
    appName = appName,
    uniqueUserId = uniqueUserId,
    createdAt = createdAt,
    apkPath = apkPath,
)