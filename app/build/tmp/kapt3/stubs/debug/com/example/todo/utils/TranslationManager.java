package com.example.todo.utils;

import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ&\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0086@\u00a2\u0006\u0002\u0010\rR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/todo/utils/TranslationManager;", "", "()V", "translators", "", "", "Lcom/google/mlkit/nl/translate/Translator;", "cleanupTranslators", "", "translateText", "text", "sourceLanguage", "targetLanguage", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TranslationManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, com.google.mlkit.nl.translate.Translator> translators = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.todo.utils.TranslationManager INSTANCE = null;
    
    private TranslationManager() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object translateText(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    java.lang.String sourceLanguage, @org.jetbrains.annotations.NotNull()
    java.lang.String targetLanguage, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    public final void cleanupTranslators() {
    }
}