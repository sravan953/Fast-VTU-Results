package com.teamidentiti.fastvturesults.ProvidersAndHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sravan on 3/15/2015.
 */
public class SemestersDbHelper extends SQLiteOpenHelper {
    /*
    Table format:
    -----+-----+---------+-------+-------------
    | ID | USN |Semester | Class | Percentage |
    -----+-----+---------+-------+-------------
    |    |     |         |       |            |
    |    |     |         |       |            |
    -----+-----+---------+-------+-------------
     */

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "semesters.db";

    public SemestersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_STATEMENT = "CREATE TABLE "+TableContract.SemestersContract.TABLE_NAME+" ("+
                TableContract.SemestersContract._ID+" INTEGER PRIMARY KEY, "+
                TableContract.SemestersContract.COLUMN_USN+" TEXT NOT NULL, "+
                TableContract.SemestersContract.COLUMN_SEMESTER+" TEXT NOT NULL, "+
                TableContract.SemestersContract.COLUMN_CLASS+" TEXT NOT NULL, "+
                TableContract.SemestersContract.COLUMN_PERCENTAGE+" TEXT NOT NULL)";
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
