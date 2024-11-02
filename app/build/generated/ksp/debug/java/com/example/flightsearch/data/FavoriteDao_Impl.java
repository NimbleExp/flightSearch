package com.example.flightsearch.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Favorite> __insertionAdapterOfFavorite;

  private AirportConverter __airportConverter;

  private final EntityDeletionOrUpdateAdapter<Favorite> __deletionAdapterOfFavorite;

  public FavoriteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorite = new EntityInsertionAdapter<Favorite>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `favorite` (`id`,`departure_code`,`destination_code`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Favorite entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __airportConverter().AirportToString(entity.getDepartureAirport());
        statement.bindString(2, _tmp);
        final String _tmp_1 = __airportConverter().AirportToString(entity.getDestinationAirport());
        statement.bindString(3, _tmp_1);
      }
    };
    this.__deletionAdapterOfFavorite = new EntityDeletionOrUpdateAdapter<Favorite>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorite` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Favorite entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object addFavorite(final Favorite favorite, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavorite.insert(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object removeFavorite(final Favorite favorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavorite.handle(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Favorite>> getAll() {
    final String _sql = "SELECT * FROM favorite ORDER BY departure_code ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favorite"}, new Callable<List<Favorite>>() {
      @Override
      @NonNull
      public List<Favorite> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDepartureAirport = CursorUtil.getColumnIndexOrThrow(_cursor, "departure_code");
          final int _cursorIndexOfDestinationAirport = CursorUtil.getColumnIndexOrThrow(_cursor, "destination_code");
          final List<Favorite> _result = new ArrayList<Favorite>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Favorite _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Airport _tmpDepartureAirport;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDepartureAirport);
            final Airport _tmp_1 = __airportConverter().StringToAirport(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.example.flightsearch.data.Airport', but it was NULL.");
            } else {
              _tmpDepartureAirport = _tmp_1;
            }
            final Airport _tmpDestinationAirport;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfDestinationAirport);
            final Airport _tmp_3 = __airportConverter().StringToAirport(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.example.flightsearch.data.Airport', but it was NULL.");
            } else {
              _tmpDestinationAirport = _tmp_3;
            }
            _item = new Favorite(_tmpId,_tmpDepartureAirport,_tmpDestinationAirport);
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
  public Object getFavorite(final Airport departureAirport, final Airport destinationAirport,
      final Continuation<? super Favorite> $completion) {
    final String _sql = "SELECT * FROM favorite WHERE departure_code = ? AND destination_code = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __airportConverter().AirportToString(departureAirport);
    _statement.bindString(_argIndex, _tmp);
    _argIndex = 2;
    final String _tmp_1 = __airportConverter().AirportToString(destinationAirport);
    _statement.bindString(_argIndex, _tmp_1);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Favorite>() {
      @Override
      @Nullable
      public Favorite call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDepartureAirport = CursorUtil.getColumnIndexOrThrow(_cursor, "departure_code");
          final int _cursorIndexOfDestinationAirport = CursorUtil.getColumnIndexOrThrow(_cursor, "destination_code");
          final Favorite _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Airport _tmpDepartureAirport;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfDepartureAirport);
            final Airport _tmp_3 = __airportConverter().StringToAirport(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.example.flightsearch.data.Airport', but it was NULL.");
            } else {
              _tmpDepartureAirport = _tmp_3;
            }
            final Airport _tmpDestinationAirport;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfDestinationAirport);
            final Airport _tmp_5 = __airportConverter().StringToAirport(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.example.flightsearch.data.Airport', but it was NULL.");
            } else {
              _tmpDestinationAirport = _tmp_5;
            }
            _result = new Favorite(_tmpId,_tmpDepartureAirport,_tmpDestinationAirport);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Arrays.asList(AirportConverter.class);
  }

  private synchronized AirportConverter __airportConverter() {
    if (__airportConverter == null) {
      __airportConverter = __db.getTypeConverter(AirportConverter.class);
    }
    return __airportConverter;
  }
}
