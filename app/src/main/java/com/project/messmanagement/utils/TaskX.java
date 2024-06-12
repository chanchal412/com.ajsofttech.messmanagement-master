package com.project.messmanagement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.project.messmanagement.R;
import com.project.messmanagement.activity.MainActivity;
import com.project.messmanagement.models.ModelRateReview;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class TaskX {
    AlertDialog dialog;
    String starred="4";
    public String replaceEmail(String email){
       // ., $, #, [, ], /
        String replacedEmail;
        replacedEmail=email.replace('.','_')
                .replace('$','_')
                .replace('#','_')
                .replace('[','_')
        .replace(']','_')
        .replace('/','_');
        return replacedEmail;
    }
    public AlertDialog addReview(Context context, String managerNm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.alert_review, (ViewGroup) null);
        builder.setView(inflate);
        AlertDialog show = builder.show();
        dialog =show;
        TextInputEditText etReview=inflate.findViewById(R.id.etReview);
        ImageView imgRate1=inflate.findViewById(R.id.imgRate1);
        ImageView imgRate2=inflate.findViewById(R.id.imgRate2);
        ImageView imgRate3=inflate.findViewById(R.id.imgRate3);
        ImageView imgRate4=inflate.findViewById(R.id.imgRate4);
        ImageView imgRate5=inflate.findViewById(R.id.imgRate5);

        imgRate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starred="1";
                imgRate1.setImageResource(R.drawable.starx);
                imgRate2.setImageResource(R.drawable.stary);
                imgRate3.setImageResource(R.drawable.stary);
                imgRate4.setImageResource(R.drawable.stary);
                imgRate5.setImageResource(R.drawable.stary);
            }
        });
        imgRate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starred="2";
                imgRate1.setImageResource(R.drawable.starx);
                imgRate2.setImageResource(R.drawable.starx);
                imgRate3.setImageResource(R.drawable.stary);
                imgRate4.setImageResource(R.drawable.stary);
                imgRate5.setImageResource(R.drawable.stary);
            }
        });
        imgRate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starred="3";
                imgRate1.setImageResource(R.drawable.starx);
                imgRate2.setImageResource(R.drawable.starx);
                imgRate3.setImageResource(R.drawable.starx);
                imgRate4.setImageResource(R.drawable.stary);
                imgRate5.setImageResource(R.drawable.stary);
            }
        });
        imgRate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starred="4";
                imgRate1.setImageResource(R.drawable.starx);
                imgRate2.setImageResource(R.drawable.starx);
                imgRate3.setImageResource(R.drawable.starx);
                imgRate4.setImageResource(R.drawable.starx);
                imgRate5.setImageResource(R.drawable.stary);
            }
        });
        imgRate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starred="5";
                imgRate1.setImageResource(R.drawable.starx);
                imgRate2.setImageResource(R.drawable.starx);
                imgRate3.setImageResource(R.drawable.starx);
                imgRate4.setImageResource(R.drawable.starx);
                imgRate5.setImageResource(R.drawable.starx);
            }
        });

        inflate.findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etReview.getText().toString().equals("")){
                    addRateReview(managerNm,etReview.getText().toString(),starred);
                }else {
                    etReview.setError("Please fill valid review");
                }

            }
        });

        return show;
    }

    private void addRateReview(String managerNm,String review,String starred) {
        FirebaseDatabase.getInstance().getReference()
                .child("Database")
                .child("RateReview")
                .child(managerNm)
                .child(replaceEmail(MainActivity.modelRegisterUser.emailAddress))
                .setValue(new ModelRateReview(review,MainActivity.modelRegisterUser.emailAddress,starred,MainActivity.modelRegisterUser.userName)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UtilUITask.getToastMessage(MainActivity.activity,"Submitted your review....");
                dialog.dismiss();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        UtilUITask.getToastMessage(MainActivity.activity,"Submitted your review....");
                    }
                });
    }
}
