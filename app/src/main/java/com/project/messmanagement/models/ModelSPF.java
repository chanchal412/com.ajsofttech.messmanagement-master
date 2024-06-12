package com.project.messmanagement.models;

import android.content.SharedPreferences;

public class ModelSPF {
    public SharedPreferences.Editor editor;
    public String key;
    public SharedPreferences sharedPreferences;

    public ModelSPF(SharedPreferences sharedPreferences2, SharedPreferences.Editor editor2, String str) {
        this.sharedPreferences = sharedPreferences2;
        this.editor = editor2;
        this.key = str;
    }

    public SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences2) {
        this.sharedPreferences = sharedPreferences2;
    }

    public SharedPreferences.Editor getEditor() {
        return this.editor;
    }

    public void setEditor(SharedPreferences.Editor editor2) {
        this.editor = editor2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }
}
