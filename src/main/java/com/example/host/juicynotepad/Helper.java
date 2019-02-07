package com.example.host.juicynotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

class Helper {
    static DBHelper dbHelper;
    static SQLiteDatabase db;
    static List<Note> noteList;
    static NotesRVAdapter mAdapter;
    static String tableName;

    static class DBInsert extends AsyncTask<Object, Void, Void> {
        String tableName;
        ContentValues contentValues;

        @Override
        protected Void doInBackground(Object... objects) {
            tableName = (String) objects[0];
            contentValues = (ContentValues) objects[1];

            db.insert(tableName, null, contentValues);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                Helper.mAdapter.updateView(Helper.readFromDB());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class DBDelete extends AsyncTask<Object, Void, Void> {
        String tableName;
        ContentValues contentValues;
        List<Note> notes;
        String[] ids;

        @Override
        @SuppressWarnings({"unchecked"})
        protected Void doInBackground( Object...  objects) {

            tableName = (String) objects[0];
            notes = (List<Note>) objects[1];
            ids = new String[notes.size()];

            for(int i = 0; i < ids.length; i++){
                ids[i] = String.valueOf(notes.get(i).id);
                db.delete(tableName, "id = " + ids[i], null);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                Helper.mAdapter.updateView(Helper.readFromDB());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    static class DBUpdate extends AsyncTask<Object, Void, Void> {
        String tableName;
        ContentValues contentValues;
        int id;

        @Override
        protected Void doInBackground(Object... objects) {

            tableName = (String) objects[0];
            contentValues = (ContentValues) objects[1];
            id = Integer.parseInt((String) objects[2]);

            db.update(tableName, contentValues, "id = " + id, null);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                Helper.mAdapter.updateView(Helper.readFromDB());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class DBReadAllPre extends AsyncTask<Object, Void, List<Note>> {
        SQLiteDatabase database;
        String tableName;
        List<Note> noteList;


        @Override
        protected List<Note> doInBackground(Object... objects) {

            tableName = (String) objects[0];
            noteList = new ArrayList<>();

            Cursor c = db.query(tableName, new String[]{"id", "preview", "time"}, null,
                    null,null,null, null);

            if(c.moveToFirst()){

                do{
                    int idIndex = c.getColumnIndex("id");
                    int idTime = c.getColumnIndex("time");
                    int idPre = c.getColumnIndex("preview");

                    int id = c.getInt(idIndex);
                    String time = c.getString(idTime);
                    time = formatDateTime(time);
                    String preview = c.getString(idPre);

                    noteList.add(new Note(id, time, preview));

                } while(c.moveToNext());

            } else {
                Log.d("DBLOG", "empty column");
            }

            c.close();
            return noteList;
        }
    }

    public static class DBReadSingleData extends AsyncTask<Object, Void, String> {
        String tableName;
        int id;

        @Override
        protected String doInBackground(Object... objects) {

            tableName = (String) objects[0];
            id = (int) objects[1];

            Cursor c = db.query(tableName, new String[]{"id","data"}, null,
                    null,null,null, null);

            if(c.moveToFirst()){

                do{
                    int idIndex = c.getColumnIndex("id");
                    if (id == c.getInt(idIndex)) {
                        int idData = c.getColumnIndex("data");
                        String data =  c.getString(idData);
                        c.close();
                        return data;
                    }

                } while(c.moveToNext());

            } else {
                Log.d("DBLOG", "empty column");
            }

            c.close();
            return null;
        }
    }

    static class DBClear extends AsyncTask<Object, Void, Void> {

        String tableName;

        @Override
        protected Void doInBackground(Object... objects) {

            tableName = (String) objects[0];
            int clearCount = db.delete(tableName, null, null);
            Log.d("DBLOG", "cleared rows count = " + clearCount);
            return null;
        }
    }

    static List<Note> readFromDB(){
        List<Note> readList = new ArrayList<>();
        Helper.DBReadAllPre readAll = new Helper.DBReadAllPre();
        readAll.execute(tableName);

        try{
            readList = readAll.get();
        } catch (Exception e){
            e.printStackTrace();
        }
        return  readList;
    }

    static void clearDB(String tableName){
        Helper.DBClear ereseAll = new  Helper.DBClear();
        ereseAll.execute(tableName);
    }

    private static String formatDateTime(String timeToFormat){

        Locale locale = new Locale("ru", "RU");

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);

        Calendar calendarCurrent = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();

        Date date = null;
        SimpleDateFormat hrFormat = null;

        if (timeToFormat != null) {
            try {
                calendar.setTime(iso8601Format.parse(timeToFormat));
            } catch (ParseException e) {
               e.getErrorOffset();
            }

            if(calendar.get(Calendar.YEAR)==calendarCurrent.get(Calendar.YEAR)){

                hrFormat = new SimpleDateFormat(
                        "MM-dd HH:mm", locale);
            } else {

                hrFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm", locale);
            }

            date  = calendar.getTime();

            return  hrFormat.format(date);

        } else {
            throw new NullPointerException("InputStringCannotBeEmpty");
        }
    }

    static String getCurISODateTime(){
        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);


        finalDateTime = iso8601Format.format(Calendar.getInstance().getTime());
        return finalDateTime;
    }
  
}
