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
    String id;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etData = findViewById(R.id.etEditNote);
        tableName = getString(R.string.table_name);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        if(id != null) {
            Helper.DBReadSingleData dBread = new Helper.DBReadSingleData();
            dBread.execute(tableName, Integer.parseInt(id));
            try {
                etData.setText(dBread.get());
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ContentValues cv = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        String data = etData.getText().toString();

        if (!data.isEmpty()) {
            String time = sdf.format(new Date());
            cv.put("data", data);
            cv.put("time", time);
            cv.put("preview", data.split("\n")[0]);
            if(id == null) {
                Helper.DBInsert insertObj = new Helper.DBInsert();
                insertObj.execute(tableName, cv);
            } else {
                Helper.DBUpdate updateObj = new Helper.DBUpdate();
                updateObj.execute(tableName, cv, id);
            }
        }
    }
}
