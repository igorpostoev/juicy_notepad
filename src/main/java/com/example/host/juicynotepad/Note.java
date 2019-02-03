package com.example.host.juicynotepad;

class Note {
    String data;
    String time;
    String preview;
    int id;
    private boolean isSelected = false;

    Note(int id, String t, String p){
        this.id = id;
        this.time = t;
        this.preview = p;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }
}
