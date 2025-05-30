package com.example.todo;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.todo.data.Category;
import com.example.todo.data.Priority;
import com.example.todo.data.Task;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tasks` (`id`,`title`,`description`,`dueDate`,`reminderTime`,`priority`,`category`,`isCompleted`,`installationId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getDueDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final Long _tmp_1 = __dateConverter.dateToTimestamp(entity.getReminderTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp_1);
        }
        final String _tmp_2 = __dateConverter.fromPriority(entity.getPriority());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        final String _tmp_3 = __dateConverter.fromCategory(entity.getCategory());
        if (_tmp_3 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_3);
        }
        final int _tmp_4 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_4);
        if (entity.getInstallationId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getInstallationId());
        }
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`dueDate` = ?,`reminderTime` = ?,`priority` = ?,`category` = ?,`isCompleted` = ?,`installationId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getDueDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final Long _tmp_1 = __dateConverter.dateToTimestamp(entity.getReminderTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp_1);
        }
        final String _tmp_2 = __dateConverter.fromPriority(entity.getPriority());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        final String _tmp_3 = __dateConverter.fromCategory(entity.getCategory());
        if (_tmp_3 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_3);
        }
        final int _tmp_4 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_4);
        if (entity.getInstallationId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getInstallationId());
        }
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertTask(final Task task, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTask.insertAndReturnId(task);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTask(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTask(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Task>> getAllTasks() {
    final String _sql = "SELECT `tasks`.`id` AS `id`, `tasks`.`title` AS `title`, `tasks`.`description` AS `description`, `tasks`.`dueDate` AS `dueDate`, `tasks`.`reminderTime` AS `reminderTime`, `tasks`.`priority` AS `priority`, `tasks`.`category` AS `category`, `tasks`.`isCompleted` AS `isCompleted`, `tasks`.`installationId` AS `installationId` FROM tasks ORDER BY dueDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfTitle = 1;
          final int _cursorIndexOfDescription = 2;
          final int _cursorIndexOfDueDate = 3;
          final int _cursorIndexOfReminderTime = 4;
          final int _cursorIndexOfPriority = 5;
          final int _cursorIndexOfCategory = 6;
          final int _cursorIndexOfIsCompleted = 7;
          final int _cursorIndexOfInstallationId = 8;
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final Date _tmpDueDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDueDate);
            }
            _tmpDueDate = __dateConverter.fromTimestamp(_tmp);
            final Date _tmpReminderTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = __dateConverter.fromTimestamp(_tmp_1);
            final Priority _tmpPriority;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __dateConverter.toPriority(_tmp_2);
            final Category _tmpCategory;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __dateConverter.toCategory(_tmp_3);
            final boolean _tmpIsCompleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_4 != 0;
            final String _tmpInstallationId;
            if (_cursor.isNull(_cursorIndexOfInstallationId)) {
              _tmpInstallationId = null;
            } else {
              _tmpInstallationId = _cursor.getString(_cursorIndexOfInstallationId);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpDueDate,_tmpReminderTime,_tmpPriority,_tmpCategory,_tmpIsCompleted,_tmpInstallationId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Task>> searchTasks(final String searchQuery) {
    final String _sql = "SELECT * FROM tasks WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (searchQuery == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchQuery);
    }
    _argIndex = 2;
    if (searchQuery == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchQuery);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfInstallationId = CursorUtil.getColumnIndexOrThrow(_cursor, "installationId");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final Date _tmpDueDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDueDate);
            }
            _tmpDueDate = __dateConverter.fromTimestamp(_tmp);
            final Date _tmpReminderTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = __dateConverter.fromTimestamp(_tmp_1);
            final Priority _tmpPriority;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __dateConverter.toPriority(_tmp_2);
            final Category _tmpCategory;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __dateConverter.toCategory(_tmp_3);
            final boolean _tmpIsCompleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_4 != 0;
            final String _tmpInstallationId;
            if (_cursor.isNull(_cursorIndexOfInstallationId)) {
              _tmpInstallationId = null;
            } else {
              _tmpInstallationId = _cursor.getString(_cursorIndexOfInstallationId);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpDueDate,_tmpReminderTime,_tmpPriority,_tmpCategory,_tmpIsCompleted,_tmpInstallationId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
