package com.launchersam

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.runtime.Immutable

@Immutable
data class AppItem(
    val label: String,
    val packageName: String,
    val activityName: String
)

object AppRepository {

    fun loadApps(context: Context): List<AppItem> {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        return try {
            pm.queryIntentActivities(intent, PackageManager.MATCH_ALL)
                .mapNotNull { it.toAppItem(pm) }
                .sortedBy { it.label.lowercase() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun ResolveInfo.toAppItem(pm: PackageManager): AppItem? = try {
        AppItem(
            label = loadLabel(pm)?.toString() ?: activityInfo.packageName,
            packageName = activityInfo.packageName,
            activityName = activityInfo.name
        )
    } catch (e: Exception) {
        null
    }

    fun launch(context: Context, app: AppItem) {
        try {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
                component = android.content.ComponentName(app.packageName, app.activityName)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // App missing or disabled; ignore so launcher does not crash
        }
    }
}
