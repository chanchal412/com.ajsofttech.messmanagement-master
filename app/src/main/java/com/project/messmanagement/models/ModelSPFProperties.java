package com.project.messmanagement.models;

import android.content.SharedPreferences;

public class ModelSPFProperties {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ModelSPFProperties(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    public ModelSPFProperties() {
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}
