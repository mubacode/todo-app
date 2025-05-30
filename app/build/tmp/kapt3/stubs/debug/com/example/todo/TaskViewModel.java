package com.example.todo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.example.todo.data.Task;
import com.example.todo.data.Priority;
import com.example.todo.data.Category;
import kotlinx.coroutines.flow.*;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0006\u0010!\u001a\u00020\u001dJ\u000e\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\tJ\u000e\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u0007J\u0006\u0010&\u001a\u00020\u001dJ\u0016\u0010\'\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015\u00a8\u0006("}, d2 = {"Lcom/example/todo/TaskViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_currentFilter", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/todo/TaskFilter;", "_searchQuery", "", "_syncStatus", "Lcom/example/todo/SyncStatus;", "currentFilter", "currentSearch", "database", "Lcom/example/todo/TaskDatabase;", "repository", "Lcom/example/todo/TaskRepository;", "syncStatus", "Lkotlinx/coroutines/flow/StateFlow;", "getSyncStatus", "()Lkotlinx/coroutines/flow/StateFlow;", "taskDao", "Lcom/example/todo/TaskDao;", "tasks", "", "Lcom/example/todo/data/Task;", "getTasks", "addTask", "", "task", "(Lcom/example/todo/data/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTask", "refreshTasks", "searchTasks", "query", "setFilter", "filter", "syncWithCloud", "updateTask", "app_debug"})
public final class TaskViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.todo.TaskDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.todo.TaskDao taskDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.todo.TaskRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.todo.TaskFilter> _currentFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.todo.SyncStatus> _syncStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.todo.SyncStatus> syncStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.todo.data.Task>> tasks = null;
    @org.jetbrains.annotations.NotNull()
    private com.example.todo.TaskFilter currentFilter = com.example.todo.TaskFilter.ALL;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentSearch = "";
    
    public TaskViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.todo.SyncStatus> getSyncStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.todo.data.Task>> getTasks() {
        return null;
    }
    
    public final void refreshTasks() {
    }
    
    public final void setFilter(@org.jetbrains.annotations.NotNull()
    com.example.todo.TaskFilter filter) {
    }
    
    public final void searchTasks(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addTask(@org.jetbrains.annotations.NotNull()
    com.example.todo.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateTask(@org.jetbrains.annotations.NotNull()
    com.example.todo.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteTask(@org.jetbrains.annotations.NotNull()
    com.example.todo.data.Task task, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void syncWithCloud() {
    }
}