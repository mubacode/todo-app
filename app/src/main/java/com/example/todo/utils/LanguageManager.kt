package com.example.todo.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import java.util.*

object LanguageManager {
    private const val TAG = "LanguageManager"
    private const val DEFAULT_LANGUAGE = "en"
    
    private val supportedLanguages = setOf("en", "tr", "pl", "de", "fr", "es", "it")
    private var currentLocale: Locale = Locale.getDefault()

    fun updateLocale(context: Context, languageCode: String): Context {
        if (!isLanguageSupported(languageCode)) {
            Log.w(TAG, "Unsupported language code: $languageCode, falling back to $DEFAULT_LANGUAGE")
            return updateLocale(context, DEFAULT_LANGUAGE)
        }

        return try {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            currentLocale = locale
            
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            
            context.createConfigurationContext(config)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating locale to $languageCode", e)
            context
        }
    }

    fun getCurrentLanguage(): String {
        return currentLocale.language.takeIf { isLanguageSupported(it) } ?: DEFAULT_LANGUAGE
    }

    fun isLanguageSupported(languageCode: String): Boolean {
        return supportedLanguages.contains(languageCode)
    }

    fun getSupportedLanguages(): Set<String> {
        return supportedLanguages.toSet()
    }

    fun getDisplayName(languageCode: String, inLocale: Locale = currentLocale): String {
        return try {
            Locale(languageCode).getDisplayLanguage(inLocale).capitalize(inLocale)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting display name for $languageCode", e)
            languageCode
        }
    }
} 