package com.example.host.juicynotepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {
    EditText etData;
    DBHelper dbHelper;
    SQLiteDatabase sdb;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        etData = findViewById(R.id.etEditNote);
        dbHelper = new DBHelper(this);
        sdb = dbHelper.getWritableDatabase();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if(id.isEmpty()) {
           Log.d("Log_NOTES_ACTIVITY", "DESTROYED");
           //TODO: connection to db on another thread!
           //Определиться, как выводить первую строку в списке заметок:  хранить в базе первую строку отдельно в пале типа brief_text или как-то иначе.

           ContentValues cv = new ContentValues();
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);


           String data = etData.getText().toString();
           if (!data.isEmpty()) {
               String time = sdf.format(new Date());
               cv.put("data", data);
               cv.put("time", time);
               cv.put("preview", data.split("\n")[0]);
           }
           Helper.DBInsert insertObj = new Helper.DBInsert();
           insertObj.execute(sdb, cv);
       }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOg_NOTES_ACTIVITY", "PAUSED");
    }

    @Override
    protected void onStop() {
        Log.d("LOg_NOTES_ACTIVITY", "STOPPED");
        super.onStop();
    }
}
