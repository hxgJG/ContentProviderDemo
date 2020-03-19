package com.example.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class DatabaseProvider extends ContentProvider {
    public static final String TAG = "DatabaseProvider";

    private MyDBHelper myDBHelper;

    public static final int USER_FIRST = 0;
    public static final int USER_SECOND = 1;

    //创建authority
    public static final String AUTHORITY = "com.example.contentproviderdemo.DatabaseProvider";
    private static UriMatcher uriMatcher;

    public static final Uri CONTENT_URI_FIRST = Uri.parse("content://" + AUTHORITY + "/first");
    public static final Uri CONTENT_URI_SECOND = Uri.parse("content://" + AUTHORITY + "/second");

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "first", USER_FIRST);
        uriMatcher.addURI(AUTHORITY, "second", USER_SECOND);
    }

    public DatabaseProvider() {
        Log.e(TAG, "DatabaseProvider: ");
    }

    @Override
    public boolean onCreate() {
        myDBHelper = new MyDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (myDBHelper == null || uriMatcher == null) {
            return null;
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case USER_FIRST:
                queryBuilder.setTables(MyDBHelper.TABLE_FIRST_NAME);
                break;
            case USER_SECOND:
                queryBuilder.setTables(MyDBHelper.TABLE_SECOND_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }

        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (myDBHelper == null || uriMatcher == null) {
            return null;
        }
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        String tableName;
        Uri mUri;
        switch (uriMatcher.match(uri)) {
            case USER_FIRST:
                tableName = MyDBHelper.TABLE_FIRST_NAME;
                mUri = CONTENT_URI_FIRST;
                break;
            case USER_SECOND:
                tableName = MyDBHelper.TABLE_SECOND_NAME;
                mUri = CONTENT_URI_SECOND;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        long rowId = db.insert(tableName, null, values);
        if (rowId > 0) {
            return ContentUris.withAppendedId(mUri, rowId);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (myDBHelper == null || uriMatcher == null) {
            return 0;
        }
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case USER_FIRST:
                count = db.delete(MyDBHelper.TABLE_FIRST_NAME, selection, selectionArgs);
                break;
            case USER_SECOND:
                count = db.delete(MyDBHelper.TABLE_SECOND_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (myDBHelper == null || uriMatcher == null) {
            return 0;
        }
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case USER_FIRST:
                count = db.update(MyDBHelper.TABLE_FIRST_NAME, values, selection, selectionArgs);
                break;
            case USER_SECOND:
                count = db.update(MyDBHelper.TABLE_SECOND_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }
}
