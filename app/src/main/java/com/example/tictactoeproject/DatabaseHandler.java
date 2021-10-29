package com.example.tictactoeproject;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.DropBoxManager;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataDatabase";
    public static final String TABLE_DATA = "DATA";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SCORE = "score";
    public static final String CREATE_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DATA + "("
            + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_SCORE + " INTEGER)";
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creates Table
        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public void insertData(String name, Integer score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SCORE, score);

        db.insert(TABLE_DATA, null, values);
        db.close();
    }

    public Cursor allScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DATA;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
