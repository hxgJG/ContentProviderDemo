package com.example.contentproviderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 提供provider的APP
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void query(View view) {
        Cursor cursor = getContentResolver().query(DatabaseProvider.CONTENT_URI_FIRST,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            Log.w("hxg", "name:" + name + ", time:" + time);
            cursor.moveToNext();
        }
        cursor.close();
    }
}
