package com.example.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo.data.Task;
import com.example.todo.data.Priority;
import com.example.todo.data.Category;
import com.example.todo.notification.ReminderManager;
import com.example.todo.utils.DateTimeUtils;
import com.example.todo.utils.LanguageManager;
import com.example.todo.utils.StringUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JB\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\b\u0010 \u001a\u00020\u0017H\u0002J\u0012\u0010!\u001a\u00020\u00172\b\u0010\"\u001a\u0004\u0018\u00010#H\u0014J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'H\u0016J\b\u0010(\u001a\u00020\u0017H\u0014J\u0010\u0010)\u001a\u00020%2\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\u00172\u0006\u0010-\u001a\u00020\tH\u0016JB\u0010.\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\b\u0010/\u001a\u00020\u0017H\u0002J\u0018\u00100\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011H\u0002J\b\u00101\u001a\u00020\u0017H\u0002J\b\u00102\u001a\u00020\u0017H\u0002J\b\u00103\u001a\u00020\u0017H\u0002J\u0018\u00104\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\b\u00105\u001a\u00020\u0017H\u0002J\u0010\u00106\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u0011H\u0002J\u0010\u00107\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002JJ\u00108\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u00109\u001a\u00020:2\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\u0014\u0010;\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\u0010\u0010<\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0011H\u0002J%\u0010=\u001a\u00020%2\u0016\u0010>\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010:0?\"\u0004\u0018\u00010:H\u0002\u00a2\u0006\u0002\u0010@R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2 = {"Lcom/example/todo/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "addButton", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "languageCodes", "", "", "languageNames", "", "languageSpinner", "Landroid/widget/Spinner;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "reminderManager", "Lcom/example/todo/notification/ReminderManager;", "searchEditText", "Landroid/widget/EditText;", "taskAdapter", "Lcom/example/todo/TaskAdapter;", "taskViewModel", "Lcom/example/todo/TaskViewModel;", "handleDialogSave", "", "task", "Lcom/example/todo/data/Task;", "titleEditText", "descriptionEditText", "dueDateEditText", "reminderTimeEditText", "prioritySpinner", "categorySpinner", "initializeViews", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onDestroy", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onTrimMemory", "level", "populateExistingData", "setupAddButton", "setupDateTimePickers", "setupLanguageSpinner", "setupRecyclerView", "setupSearchBar", "setupSpinners", "setupViewModel", "showDatePicker", "showDeleteConfirmation", "showDialog", "dialogView", "Landroid/view/View;", "showTaskDialog", "showTimePicker", "validateDialogViews", "views", "", "([Landroid/view/View;)Z", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.todo.TaskAdapter taskAdapter;
    private com.example.todo.TaskViewModel taskViewModel;
    private android.widget.EditText searchEditText;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.google.android.material.floatingactionbutton.FloatingActionButton addButton;
    private android.widget.Spinner languageSpinner;
    private com.example.todo.notification.ReminderManager reminderManager;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> languageCodes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> languageNames = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeViews() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupViewModel() {
    }
    
    private final void setupSearchBar() {
    }
    
    private final void setupAddButton() {
    }
    
    private final void showTaskDialog(com.example.todo.data.Task task) {
    }
    
    private final boolean validateDialogViews(android.view.View... views) {
        return false;
    }
    
    private final void setupSpinners(android.widget.Spinner prioritySpinner, android.widget.Spinner categorySpinner) {
    }
    
    private final void populateExistingData(com.example.todo.data.Task task, android.widget.EditText titleEditText, android.widget.EditText descriptionEditText, android.widget.EditText dueDateEditText, android.widget.EditText reminderTimeEditText, android.widget.Spinner prioritySpinner, android.widget.Spinner categorySpinner) {
    }
    
    private final void setupDateTimePickers(android.widget.EditText dueDateEditText, android.widget.EditText reminderTimeEditText) {
    }
    
    private final void showDatePicker(android.widget.EditText dueDateEditText) {
    }
    
    private final void showTimePicker(android.widget.EditText reminderTimeEditText) {
    }
    
    private final void showDialog(com.example.todo.data.Task task, android.view.View dialogView, android.widget.EditText titleEditText, android.widget.EditText descriptionEditText, android.widget.EditText dueDateEditText, android.widget.EditText reminderTimeEditText, android.widget.Spinner prioritySpinner, android.widget.Spinner categorySpinner) {
    }
    
    private final void handleDialogSave(com.example.todo.data.Task task, android.widget.EditText titleEditText, android.widget.EditText descriptionEditText, android.widget.EditText dueDateEditText, android.widget.EditText reminderTimeEditText, android.widget.Spinner prioritySpinner, android.widget.Spinner categorySpinner) {
    }
    
    private final void showDeleteConfirmation(com.example.todo.data.Task task) {
    }
    
    private final void setupLanguageSpinner() {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    public void onTrimMemory(int level) {
    }
}