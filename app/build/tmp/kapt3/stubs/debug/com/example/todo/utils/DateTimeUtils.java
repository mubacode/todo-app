package com.example.todo.utils;

import android.content.Context;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u000eJ\b\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/example/todo/utils/DateTimeUtils;", "", "()V", "DATE_PATTERN", "", "TAG", "TIME_PATTERN", "currentLocale", "Ljava/util/Locale;", "dateFormat", "Ljava/text/SimpleDateFormat;", "timeFormat", "formatDate", "date", "Ljava/util/Date;", "formatTime", "time", "getDateFormat", "getTimeFormat", "parseDate", "dateStr", "parseTime", "timeStr", "updateLocale", "", "locale", "app_debug"})
public final class DateTimeUtils {
    @org.jetbrains.annotations.NotNull()
    private static java.util.Locale currentLocale;
    @org.jetbrains.annotations.Nullable()
    private static java.text.SimpleDateFormat dateFormat;
    @org.jetbrains.annotations.Nullable()
    private static java.text.SimpleDateFormat timeFormat;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DATE_PATTERN = "MMM dd, yyyy";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TIME_PATTERN = "HH:mm";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DateTimeUtils";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.todo.utils.DateTimeUtils INSTANCE = null;
    
    private DateTimeUtils() {
        super();
    }
    
    public final void updateLocale(@org.jetbrains.annotations.NotNull()
    java.util.Locale locale) {
    }
    
    private final java.text.SimpleDateFormat getDateFormat() {
        return null;
    }
    
    private final java.text.SimpleDateFormat getTimeFormat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatDate(@org.jetbrains.annotations.NotNull()
    java.util.Date date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatTime(@org.jetbrains.annotations.NotNull()
    java.util.Date time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date parseDate(@org.jetbrains.annotations.NotNull()
    java.lang.String dateStr) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date parseTime(@org.jetbrains.annotations.NotNull()
    java.lang.String timeStr) {
        return null;
    }
}