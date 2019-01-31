package com.example.host.juicynotepad;

public class Note {
    String data;
    String time;
    String preview;
    int id;

    Note(int id, String t, String p){
        this.id = id;
        this.time = t;
        this.preview = p;
    }
}
