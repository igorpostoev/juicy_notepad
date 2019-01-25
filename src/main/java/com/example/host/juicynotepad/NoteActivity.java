package com.example.host.juicynotepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LOg_NOTES_ACTIVITY", "DESTROTED");
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
