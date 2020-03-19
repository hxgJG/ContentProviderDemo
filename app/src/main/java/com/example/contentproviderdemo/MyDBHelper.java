package com.example.contentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

//创建自定义类继承SQLiteOpenHelper，实现两个方法，和一个构造方法
public class MyDBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "providertest.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_FIRST_NAME = "first";
    public static final String TABLE_SECOND_NAME = "second";
    public static final String SQL_CREATE_TABLE_FIRST = "CREATE TABLE " +TABLE_FIRST_NAME +"("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "table_name" +" VARCHAR(50) default 'first',"
            + "name" + " VARCHAR(50),"
            + "time" + " VARCHAR(50)"
            + ");" ;
    public static final String SQL_CREATE_TABLE_SECOND = "CREATE TABLE "+TABLE_SECOND_NAME+" ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "table_name" +" VARCHAR(50) default 'second',"
            + "name" + " VARCHAR(50),"
            + "time" + " VARCHAR(50)"
            + ");" ;

    //构造方法
    private Context context;
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    //重写onCreate（）方法，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL(SQL_CREATE_TABLE_FIRST);
        db.execSQL(SQL_CREATE_TABLE_SECOND);
        Log.v("hxg", "Create successful");

    }
    //重写onUpgrade（）此方法在更新数据表用到，现在不用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS first");
        db.execSQL("DROP TABLE IF EXISTS second");
        onCreate(db);
    }
}