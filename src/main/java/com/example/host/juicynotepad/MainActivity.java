package com.example.host.juicynotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvMain;
    ImageButton imgBut;
    DBHelper dbHelper;
    List<Note> noteList = new ArrayList<>();
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

        noteList.add(new Note("NOTE1", "12.05.17"));
        noteList.add(new Note("NOTE2", "12.05.17"));
        noteList.add(new Note("NOTE3", "12.05.17"));
        NotesRVAdapter notesAdapter = new NotesRVAdapter(noteList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        NotesRVAdapter.VerticalItemDecoration decoration = new NotesRVAdapter.VerticalItemDecoration(15);
        rvMain.addItemDecoration(decoration);
        rvMain.setLayoutManager(llm);
        rvMain.setAdapter(notesAdapter);


    }

    @Override
    public void onClick(View v){

    }
}


