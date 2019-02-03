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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.host.juicynotepad.Helper.clearDB;
import static com.example.host.juicynotepad.Helper.db;
import static com.example.host.juicynotepad.Helper.dbHelper;
import static com.example.host.juicynotepad.Helper.mAdapter;
import static com.example.host.juicynotepad.Helper.noteList;
import static com.example.host.juicynotepad.Helper.readFromDB;
import static com.example.host.juicynotepad.Helper.tableName;


public class MainActivity extends AppCompatActivity implements  NotesRVAdapter.Selectable {
    RecyclerView rvMain;
    ImageButton imgBut;
    boolean isSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NotesRVAdapter notesAdapter;

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setElevation(0);
        }

        imgBut = findViewById(R.id.imgBtn);
        rvMain = findViewById(R.id.rvMain);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        tableName = getString(R.string.table_name);
        noteList = readFromDB();

        notesAdapter = new NotesRVAdapter(noteList, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        NotesRVAdapter.VerticalItemDecoration decoration = new NotesRVAdapter.VerticalItemDecoration(15);
        rvMain.addItemDecoration(decoration);
        rvMain.setLayoutManager(llm);
        rvMain.setAdapter(notesAdapter);

        Helper.mAdapter = notesAdapter;

        imgBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!isSelection){
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);

            } else {
                setSelectionMode(false);
                List<Note> listToDelete = Helper.mAdapter.getSelectedItems();
                Helper.DBDelete dbDelete = new Helper.DBDelete();
                dbDelete.execute(tableName, listToDelete);
            }
            }
        });
    }

    public void setSelectionMode(boolean isActivated){
        ImageButton imgBtn = findViewById(R.id.imgBtn);
        isSelection = isActivated;
        if(isActivated){
            imgBtn.setImageResource(R.drawable.garbage);
        } else {
            imgBtn.setImageResource(R.drawable.add_black);
        }
    }

    public boolean isSelectionMode(){
        return isSelection;
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
