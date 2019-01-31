package com.example.host.juicynotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class Helper {

    static List<Note> noteList;
    static NotesRVAdapter mAdapter;

    static class DBInsert extends AsyncTask<Object, Void, Void> {
        SQLiteDatabase database;
        String tableName;
        ContentValues contentValues;

        @Override
        protected Void doInBackground(Object... objects) {
            database = (SQLiteDatabase) objects[0];
            tableName = (String) objects[1];
            contentValues = (ContentValues) objects[2];

            long id = database.insert(tableName, null, contentValues);
            Log.d("DBLOG", "row inserted, ID = " + id);
            return null;
        }

    }

    static class DBDelete extends AsyncTask<Object, Void, Void> {
        SQLiteDatabase database;
        String tableName;
        ContentValues contentValues;
        int id;
        @Override
        protected Void doInBackground(Object... objects) {
            database = (SQLiteDatabase) objects[0];
            tableName = (String) objects[1];
            id = (int)objects[2];

            long count = database.delete(tableName, "id = " + id, null);
            Log.d("DBLOG", "row inserted, ID = " + id);
            return null;
        }
    }

    static class DBUpdate extends AsyncTask<Object, Void, Void> {
        SQLiteDatabase database;
        String tableName;
        ContentValues contentValues;
        int id;

        @Override
        protected Void doInBackground(Object... objects) {
            database = (SQLiteDatabase) objects[0];
            tableName = (String) objects[1];
            contentValues = (ContentValues) objects[2];
            id = (int)objects[3];

            long count = database.update(tableName, contentValues, "id = " + id, null);
            Log.d("DBLOG", "row updated, id = " + id);
            return null;
        }

    }

    static class DBReadAllPre extends AsyncTask<Object, Void, List<Note>> {
        SQLiteDatabase database;
        String tableName;
        List<Note> noteList;

        @Override
        protected List<Note> doInBackground(Object... objects) {
            database = (SQLiteDatabase) objects[0];
            tableName = (String) objects[1];

            noteList = new ArrayList<>();

            Cursor c = database.query(tableName, new String[]{"preview", "time"}, null,
                    null,null,null, null);

            Log.d("DBLOG", "Rows have been read");

            if(c.moveToFirst()){
                int idIndex = c.getColumnIndex("id");
                int idTime = c.getColumnIndex("time");
                int idPre = c.getColumnIndex("preview");
                String time = c.getString(idTime);
                String preview = c.getString(idPre);

                do{
                    Log.d("DBLOG",
                            "id = " + idIndex +
                                    "preview: " + preview +
                                    "time: " + time );

                    noteList.add(new Note(idIndex, time, preview));
                } while(c.moveToNext());

            } else {
                Log.d("DBLOG", "empty column");
            }

            c.close();
            return noteList;
        }
    }

    static class DBClear extends AsyncTask<Object, Void, Void> {
        SQLiteDatabase database;
        String tableName;
        ContentValues contentValues;

        @Override
        protected Void doInBackground(Object... objects) {
            database = (SQLiteDatabase) objects[0];
            tableName = (String) objects[1];
            contentValues = (ContentValues) objects[2];

            long id = database.insert(tableName, null, contentValues);
            Log.d("DBLOG", "row inserted, ID = " + id);
            return null;
        }
    }

}
