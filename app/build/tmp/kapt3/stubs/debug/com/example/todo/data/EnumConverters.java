package com.example.todo.data;

import androidx.room.TypeConverter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0004H\u0007\u00a8\u0006\r"}, d2 = {"Lcom/example/todo/data/EnumConverters;", "", "()V", "fromCategory", "", "category", "Lcom/example/todo/data/Category;", "fromPriority", "priority", "Lcom/example/todo/data/Priority;", "toCategory", "value", "toPriority", "app_debug"})
public final class EnumConverters {
    
    public EnumConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromPriority(@org.jetbrains.annotations.NotNull()
    com.example.todo.data.Priority priority) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.example.todo.data.Priority toPriority(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromCategory(@org.jetbrains.annotations.NotNull()
    com.example.todo.data.Category category) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.example.todo.data.Category toCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
}