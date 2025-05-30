package com.example.todo.utils

import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

object TranslationManager {
    private val translators = mutableMapOf<String, com.google.mlkit.nl.translate.Translator>()
    
    suspend fun translateText(text: String, sourceLanguage: String, targetLanguage: String): String {
        try {
            val translatorKey = "$sourceLanguage-$targetLanguage"
            val translator = translators.getOrPut(translatorKey) {
                val options = TranslatorOptions.Builder()
                    .setSourceLanguage(sourceLanguage)
                    .setTargetLanguage(targetLanguage)
                    .build()
                Translation.getClient(options)
            }

            // Download the translation model if needed
            try {
                translator.downloadModelIfNeeded().await()
                // If we reach here, model download was successful
                return translator.translate(text).await()
            } catch (e: Exception) {
                e.printStackTrace()
                return text // Return original text if model download fails
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return text // Return original text if translation fails
        }
    }

    fun cleanupTranslators() {
        translators.values.forEach { it.close() }
        translators.clear()
    }
} 