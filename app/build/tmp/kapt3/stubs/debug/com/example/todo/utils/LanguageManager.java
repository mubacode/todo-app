package com.example.todo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u0004J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u0007J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\tJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0004J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/todo/utils/LanguageManager;", "", "()V", "DEFAULT_LANGUAGE", "", "TAG", "currentLocale", "Ljava/util/Locale;", "supportedLanguages", "", "getCurrentLanguage", "getDisplayName", "languageCode", "inLocale", "getSupportedLanguages", "isLanguageSupported", "", "updateLocale", "Landroid/content/Context;", "context", "app_debug"})
public final class LanguageManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "LanguageManager";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DEFAULT_LANGUAGE = "en";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> supportedLanguages = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.Locale currentLocale;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.todo.utils.LanguageManager INSTANCE = null;
    
    private LanguageManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context updateLocale(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentLanguage() {
        return null;
    }
    
    public final boolean isLanguageSupported(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getSupportedLanguages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode, @org.jetbrains.annotations.NotNull()
    java.util.Locale inLocale) {
        return null;
    }
}