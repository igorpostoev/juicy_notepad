package com.example.host.juicynotepad;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvMain;
    ImageButton imgBut;
    DBHelper dbHelper;
    SQLiteDatabase db;
    List<Note> noteList = new ArrayList<>();
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setElevation(0);
        }

        imgBut = findViewById(R.id.imgBtn);
        rvMain = findViewById(R.id.rvMain);

        imgBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        tableName = getString(R.string.table_name);

        String data = "bla bla bla bla" + "\n" + "bla1 bla1 bla1";
        String time = "21.01.2017 23:00:01";
        ContentValues cv = new ContentValues();

            cv.put("data", data);
            cv.put("time", time);
            cv.put("preview", data.split("\n")[0]);

        Helper.DBInsert insertObj = new Helper.DBInsert();
        insertObj.execute(db,tableName, cv);

        Helper.DBReadAllPre readAll = new Helper.DBReadAllPre();

        readAll.execute(db, tableName);

        try{
            noteList = readAll.get();
        } catch (Exception e){
            e.printStackTrace();
        }

        NotesRVAdapter notesAdapter = new NotesRVAdapter(noteList, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        NotesRVAdapter.VerticalItemDecoration decoration = new NotesRVAdapter.VerticalItemDecoration(15);
        rvMain.addItemDecoration(decoration);
        rvMain.setLayoutManager(llm);
        rvMain.setAdapter(notesAdapter);

        Helper.noteList = noteList;
        Helper.mAdapter = notesAdapter;
    }

    @Override
    public void onClick(View v){

    }

    private void setLocale(Locale locale){
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else{
            configuration.locale = locale;
        }
    }
}


