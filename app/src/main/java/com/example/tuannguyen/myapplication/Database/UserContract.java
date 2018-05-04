package com.example.tuannguyen.myapplication.Database;

import android.provider.BaseColumns;

public final class UserContract implements BaseColumns {
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "( "
            + BaseColumns._ID + " INTEGER PRIMARY KEY"
            + COLUMN_NAME + " TEXT "
            + COLUMN_SCORE + "INTEGER)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
