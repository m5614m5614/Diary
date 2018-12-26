package com.example.ting.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    public static int version = 3;

    public static final String CREATE_DIARY = "create table Diary("
            + "id integer primary key autoincrement, "
            + "date text, "
            + "title text, "
            + "tag text, "
            + "content text)";

    private Context mContext;
    public DiaryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory){
        super(context, name, factory, version);
        //context=內容物件；name=傳入資料庫名稱；factory=複雜查詢時使用；version=資料庫版本
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists Diary");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade ( db, oldVersion, newVersion );
        onUpgrade(db, oldVersion, newVersion);
    }
}