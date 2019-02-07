package com.example.host.juicynotepad;

import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class Helper {
    static DBHelper dbHelper;
    static SQLiteDatabase db;
    static List<Note> noteList;
    static NotesRVAdapter mAdapter;
    static String tableName;
    static int curRowCount;

    static List<Note> readFromDB(){
        List<Note> readList = new ArrayList<>();
        ReqHelper.DBReadAllPre readAll = new ReqHelper.DBReadAllPre();
        readAll.execute(tableName);

        try{
            readList = readAll.get();
        } catch (Exception e){
            e.printStackTrace();
        }
        return  readList;
    }

    static void clearDB(String tableName){
        ReqHelper.DBClear ereseAll = new ReqHelper.DBClear();
        ereseAll.execute(tableName);
    }

     static String formatDateTime(String timeToFormat){

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
