package com.example.todo.utils;

import android.content.Context;
import android.util.Log;
import android.util.LruCache;
import com.example.todo.R;
import com.example.todo.data.Category;
import com.example.todo.data.Priority;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/todo/utils/StringUtils;", "", "()V", "CACHE_SIZE", "", "TAG", "", "stringCache", "Landroid/util/LruCache;", "clearCache", "", "getCategoryString", "context", "Landroid/content/Context;", "category", "Lcom/example/todo/data/Category;", "getPriorityString", "priority", "Lcom/example/todo/data/Priority;", "app_debug"})
public final class StringUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "StringUtils";
    private static final int CACHE_SIZE = 100;
    @org.jetbrains.annotations.NotNull()
    private static final android.util.LruCache<java.lang.String, java.lang.String> stringCache = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.todo.utils.StringUtils INSTANCE = null;
    
    private StringUtils() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPriorityString(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.todo.data.Priority priority) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategoryString(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.todo.data.Category category) {
        return null;
    }
    
    public final void clearCache() {
    }
}