package com.project.messmanagement.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.messmanagement.models.ModelSPF;

public class SPF {
    public ModelSPF getSPF(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        return new ModelSPF(sharedPreferences, sharedPreferences.edit(), str2);
    }
}
