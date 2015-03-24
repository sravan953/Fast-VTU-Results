package com.teamidentiti.fastvturesults.ProvidersAndHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sravan on 2/19/2015.
 */
public class ResultsDbHelper extends SQLiteOpenHelper {
    /*
    Table format:
    -----+-----+----------+---------+-----------------+------
    | ID | USN | SEMESTER | HEADER1 | HEADER2 ... TOT | RES |
    -----+-----+----------+---------+-----------------+------
    |    |     |          |         |                 |     |
    |    |     |          |         |                 |     |
    -----+-----+----------+---------+-----------------+------
     */

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "results.db";

    public ResultsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_STATEMENT = "CREATE TABLE "+TableContract.ResultsContract.TABLE_NAME+" ("+
                TableContract.ResultsContract._ID+" INTEGER PRIMARY KEY, "+
                TableContract.ResultsContract.COLUMN_USN+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_SEMESTER+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_HEADER1+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_HEADER2+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_HEADER3+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_HEADER4+" TEXT NOT NULL, "+

                TableContract.ResultsContract.COLUMN_SUBJECT+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_CODE+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_INT+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_EXT+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_TOT+" TEXT NOT NULL, "+
                TableContract.ResultsContract.COLUMN_RES+" TEXT NOT NULL)";
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
