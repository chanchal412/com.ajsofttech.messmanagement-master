package com.project.messmanagement.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.messmanagement.models.ModelSPFProperties;

public class SharedPref {
    public ModelSPFProperties createSharedPref(Context context,String spfNm){
        SharedPreferences spf=context.getSharedPreferences(spfNm,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=spf.edit();
        return new ModelSPFProperties(spf,editor);
    }
}
