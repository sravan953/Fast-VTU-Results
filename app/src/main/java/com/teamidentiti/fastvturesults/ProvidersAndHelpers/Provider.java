package com.teamidentiti.fastvturesults.ProvidersAndHelpers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Sravan on 3/13/2015.
 */
public class Provider extends ContentProvider {
    private static final String CONTENT_AUTHORITY="com.teamidentiti.fastvturesults";

    private static final String PATH_RESULTS="results";
    public static final String PATH_SEMESTERS="semesters";

    private static final Uri BASE_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI_RESULTS= BASE_URI.buildUpon().appendPath(PATH_RESULTS).build();
    public static final Uri CONTENT_URI_SEMESTERS=BASE_URI.buildUpon().appendPath(PATH_SEMESTERS).build();

    private ResultsDbHelper resultsDbHelper;
    private SemestersDbHelper semestersDbHelper;

    private static UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        resultsDbHelper =new ResultsDbHelper(getContext());
        semestersDbHelper=new SemestersDbHelper(getContext());

        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_RESULTS, 1);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_SEMESTERS, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c;
        if(uriMatcher.match(uri)==1)
            c=resultsDbHelper.getReadableDatabase().query(TableContract.ResultsContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        else
            c=semestersDbHelper.getReadableDatabase().query(TableContract.SemestersContract.TABLE_NAME, projection, selection , selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(uriMatcher.match(uri)==1) {
            SQLiteDatabase db = resultsDbHelper.getWritableDatabase();
            long id = db.insert(TableContract.ResultsContract.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(CONTENT_URI_RESULTS, id);
        }
        else {
            SQLiteDatabase db = semestersDbHelper.getWritableDatabase();
            long id = db.insert(TableContract.SemestersContract.TABLE_NAME, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(CONTENT_URI_SEMESTERS, id);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
