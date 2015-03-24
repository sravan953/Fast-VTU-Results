package com.teamidentiti.fastvturesults.ProvidersAndHelpers;

import android.provider.BaseColumns;

/**
 * Created by Sravan on 2/19/2015.
 */
public class TableContract {

    public class ResultsContract implements BaseColumns {
        public static final String TABLE_NAME = "results";

        public static final String COLUMN_USN = "usn";
        public static final String COLUMN_SEMESTER = "semester";
        public static final String COLUMN_HEADER1 = "header1";
        public static final String COLUMN_HEADER2 = "header2";
        public static final String COLUMN_HEADER3 = "header3";
        public static final String COLUMN_HEADER4 = "header4";

        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_INT = "int";
        public static final String COLUMN_EXT = "ext";
        public static final String COLUMN_TOT = "tot";
        public static final String COLUMN_RES = "res";
    }

    public class SemestersContract implements BaseColumns {
        public static final String TABLE_NAME = "semesters";

        public static final String COLUMN_USN = "usn";
        public static final String COLUMN_SEMESTER = "semester";
        public static final String COLUMN_CLASS = "class";
        public static final String COLUMN_PERCENTAGE = "percentage";
    }
}
