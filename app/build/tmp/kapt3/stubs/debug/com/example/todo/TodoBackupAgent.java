package com.example.todo;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.os.ParcelFileDescriptor;
import com.google.firebase.firestore.FirebaseFirestore;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016J\"\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\r\u001a\u0004\u0018\u00010\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/example/todo/TodoBackupAgent;", "Landroid/app/backup/BackupAgent;", "()V", "installationId", "", "getInstallationId", "()Ljava/lang/String;", "onBackup", "", "oldState", "Landroid/os/ParcelFileDescriptor;", "data", "Landroid/app/backup/BackupDataOutput;", "newState", "onRestore", "Landroid/app/backup/BackupDataInput;", "appVersionCode", "", "Companion", "app_debug"})
public final class TodoBackupAgent extends android.app.backup.BackupAgent {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TASKS_BACKUP_KEY = "tasks_backup";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.todo.TodoBackupAgent.Companion Companion = null;
    
    public TodoBackupAgent() {
        super();
    }
    
    private final java.lang.String getInstallationId() {
        return null;
    }
    
    @java.lang.Override()
    public void onBackup(@org.jetbrains.annotations.Nullable()
    android.os.ParcelFileDescriptor oldState, @org.jetbrains.annotations.NotNull()
    android.app.backup.BackupDataOutput data, @org.jetbrains.annotations.NotNull()
    android.os.ParcelFileDescriptor newState) {
    }
    
    @java.lang.Override()
    public void onRestore(@org.jetbrains.annotations.NotNull()
    android.app.backup.BackupDataInput data, int appVersionCode, @org.jetbrains.annotations.Nullable()
    android.os.ParcelFileDescriptor newState) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/todo/TodoBackupAgent$Companion;", "", "()V", "TASKS_BACKUP_KEY", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}