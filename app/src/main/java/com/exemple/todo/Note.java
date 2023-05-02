package com.exemple.todo;

import android.widget.ImageButton;

import com.google.firebase.Timestamp;

import com.google.firebase.Timestamp;

public class Note {
    String title;
    String content;
    Timestamp timestamp;
    ImageButton deleteButton;





    public Note() {
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(ImageButton delete) {
        this.deleteButton = delete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

