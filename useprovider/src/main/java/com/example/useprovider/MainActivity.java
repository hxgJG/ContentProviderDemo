package com.example.useprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用provider的APP
 */
public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.example.contentproviderdemo.DatabaseProvider";
    public static final Uri CONTENT_URI_FIRST = Uri.parse("content://" + AUTHORITY + "/first");
    public static final Uri CONTENT_URI_SECOND = Uri.parse("content://" + AUTHORITY + "/second");
    public static Uri currentURI = CONTENT_URI_FIRST;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
    }

    private String formatDate() {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss.SSS");
        String time = sdf.format(new Date());
        Log.v("hxg", "time:" + time);
        return time;
    }

    public void insert(View view) {
        String name = input.getText().toString();
        //创建期待匹配的uri
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("time",formatDate());
        //获得ContentResolver对象，调用方法
        Uri uri = getContentResolver().insert(currentURI, values);
        Log.d("hxg", "insert result: uri = " + uri);
    }

    public void delete(View view) {
        String name = input.getText().toString();
        int count = getContentResolver().delete(currentURI,"name=?",new String[]{name});
        Log.d("hxg", "delete: count = " + count);
    }

    public void update(View view) {
        String updateStr = input.getText().toString();
        if (updateStr.isEmpty()) {
            Toast.makeText(this, "先输入", Toast.LENGTH_LONG).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("name",updateStr);
        int count = getContentResolver().update(currentURI, values,"_id=1",null);
        Log.d("hxg", "update: count = " + count);
    }

    public void query(View view) {
        Cursor cursor = getContentResolver().query(currentURI,null,null,null,null);
        cursor.moveToFirst();
        Log.i("hxg", "***********************************************");
        do{
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            Log.i("hxg", name + ": " + time);
        }while(cursor.moveToNext());
        Log.i("hxg", "-----------------------------------------------");
        cursor.close();
    }

    public void useFirst(View view) {
        if (currentURI != CONTENT_URI_FIRST) {
            currentURI = CONTENT_URI_FIRST;
        }
    }

    public void useSecond(View view) {
        if (currentURI != CONTENT_URI_SECOND) {
            currentURI = CONTENT_URI_SECOND;
        }
    }
}
