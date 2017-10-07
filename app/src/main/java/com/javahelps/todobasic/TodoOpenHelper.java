package com.javahelps.todobasic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sneha on 16-09-2017.
 */

public class TodoOpenHelper extends SQLiteOpenHelper {

    private static TodoOpenHelper instance ;

    public static TodoOpenHelper getInstance(Context context) {
        if (instance == null){
            instance = new TodoOpenHelper(context);
        }
        return instance ;
    }

    private TodoOpenHelper(Context context) {
        super(context, "Todo", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + Contract.TODO_DATA_TABLE + " ( " +
                Contract.TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TODO_TASK + " TEXT, " +
                Contract.TODO_DATE + " TEXT," +
                Contract.TODO_TIME + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
