package com.example.flightsearch.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AirportDao_Impl implements AirportDao {
  private final RoomDatabase __db;

  public AirportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public Flow<List<Airport>> getAirportByName(final String input) {
    final String _sql = "SELECT * FROM airport WHERE iata_code LIKE ? OR name LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, input);
    _argIndex = 2;
    _statement.bindString(_argIndex, input);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"airport"}, new Callable<List<Airport>>() {
      @Override
      @NonNull
      public List<Airport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIataCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iata_code");
          final int _cursorIndexOfPassengers = CursorUtil.getColumnIndexOrThrow(_cursor, "passengers");
          final List<Airport> _result = new ArrayList<Airport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Airport _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIataCode;
            _tmpIataCode = _cursor.getString(_cursorIndexOfIataCode);
            final int _tmpPassengers;
            _tmpPassengers = _cursor.getInt(_cursorIndexOfPassengers);
            _item = new Airport(_tmpId,_tmpName,_tmpIataCode,_tmpPassengers);
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
  public Airport getAirportByCode(final String input) {
    final String _sql = "SELECT * FROM airport WHERE iata_code = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, input);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIataCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iata_code");
      final int _cursorIndexOfPassengers = CursorUtil.getColumnIndexOrThrow(_cursor, "passengers");
      final Airport _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpIataCode;
        _tmpIataCode = _cursor.getString(_cursorIndexOfIataCode);
        final int _tmpPassengers;
        _tmpPassengers = _cursor.getInt(_cursorIndexOfPassengers);
        _result = new Airport(_tmpId,_tmpName,_tmpIataCode,_tmpPassengers);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Flow<List<Airport>> getAllByPassengers(final int currentId) {
    final String _sql = "SELECT * FROM airport WHERE id IS NOT ? ORDER BY passengers DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"airport"}, new Callable<List<Airport>>() {
      @Override
      @NonNull
      public List<Airport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIataCode = CursorUtil.getColumnIndexOrThrow(_cursor, "iata_code");
          final int _cursorIndexOfPassengers = CursorUtil.getColumnIndexOrThrow(_cursor, "passengers");
          final List<Airport> _result = new ArrayList<Airport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Airport _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIataCode;
            _tmpIataCode = _cursor.getString(_cursorIndexOfIataCode);
            final int _tmpPassengers;
            _tmpPassengers = _cursor.getInt(_cursorIndexOfPassengers);
            _item = new Airport(_tmpId,_tmpName,_tmpIataCode,_tmpPassengers);
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
