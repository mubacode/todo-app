package com.example.todo.util

import android.content.Context
import java.util.UUID

object InstallationIdentifier {
    private const val PREF_INSTALLATION_ID = "installation_id"
    private const val PREF_NAME = "todo_app_prefs"

    fun getInstallationId(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var installationId = prefs.getString(PREF_INSTALLATION_ID, null)
        
        if (installationId == null) {
            installationId = UUID.randomUUID().toString()
            prefs.edit().putString(PREF_INSTALLATION_ID, installationId).apply()
        }
        
        return installationId
    }
} 